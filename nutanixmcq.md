
# Operating Systems (full detail for SRE)

## 1 — Big picture: what an OS is (one-liner)

An **operating system** is system software that manages hardware (CPU, memory, disks, network) and provides services to applications so they can run safely and efficiently.

---

## 2 — Core responsibilities (and why SREs care)

1. **Process & CPU management**

   * Runs programs as *processes* and *threads*.
   * Schedules CPU time (who runs when).
   * Key SRE relevance: identify CPU hogs, starvation, runaway processes.

2. **Memory management**

   * Provides each process a virtual address space.
   * Handles paging, swapping, and memory protection.
   * SRE relevance: OOM, swap thrash, memory leaks.

3. **File systems & storage**

   * Organizes files & directories, enforces permissions, handles mounts.
   * SRE relevance: disk full, corrupted FS, mount failures.

4. **I/O & device management**

   * Interfaces with hardware through drivers, handles interrupts & DMA.
   * SRE relevance: disk/NIC failures, drivers, latency.

5. **Boot & service management**

   * Bootloader → kernel → init/systemd → services.
   * SRE relevance: services failing to start after reboot.

6. **Security & isolation**

   * Users, permissions, SELinux/AppArmor, namespaces.
   * SRE relevance: permissions errors, container isolation.

---

## 3 — Processes & scheduling (practical)

### Concepts

* **Process**: program in execution (has PID, memory, file descriptors).
* **Thread**: unit of execution inside a process (shares address space).
* **Process states**: NEW → READY → RUNNING → BLOCKED/WAITING → TERMINATED.
* **Context switch**: save registers/PC for one process, load another — cost: tens to hundreds of microseconds.
* **Scheduler**: decides who runs; Linux default = CFS (Completely Fair Scheduler).
* **Nice / Priority**: `nice` (lower priority), `renice` to change dynamic priority.

### Commands

* `ps aux` — list processes
* `top` / `htop` — live process view
* `ps -eo pid,ppid,cmd,%mem,%cpu --sort=-%cpu | head` — top CPU users
* `kill <pid>` (SIGTERM), `kill -9 <pid>` (SIGKILL)
* `nice -n 10 <cmd>` or `renice -n 5 -p <pid>`

### Quick checks (SRE)

* If CPU high: `top` → find PID → `strace -p <pid>` (diagnose syscalls)
* If process uses lots of memory: `pmap <pid>` or `smem` (if available)

---

## 4 — Memory management (practical)

### Concepts

* **Virtual memory**: mapping virtual addresses → physical frames via page tables.
* **Swap**: extension of RAM on disk; heavy swap usage = major slowdown.
* **OOM killer**: kernel kills process(es) when out of memory.

### Commands

* `free -m` — memory + swap summary
* `vmstat 1` — metrics including swap (si/so) and run queue
* `cat /proc/meminfo` — detailed memory info
* `pmap <pid>` — process memory map
* `dmesg | grep -i oom` — check OOM killer logs

### SRE checks

* Low memory: check `free -m`, `top` (RES vs VIRT), long-term leak: use `ps --sort -rss` to find leakers.
* Swap thrashing: high `si/so` and high iowait → consider adding RAM or tuning `swappiness` via `sysctl vm.swappiness`.

---

## 5 — File systems & storage (practical)

### Concepts

* **Filesystem types**: ext4, XFS, NTFS, etc.
* **Mount points**: everything is a single tree; mounts attach FS.
* **Inode**: metadata node for file.
* **Permissions**: `rwx` for user/group/others; SUID/SGID/sticky bits control special behaviors.

### Commands

* `df -h` — disk usage by mount
* `du -sh /path/*` — find big directories
* `ls -l` — permissions
* `chmod`, `chown` — change perms/owner
* `mount` / `umount` — mounts
* `lsblk -f` — block devices and filesystems
* `xfs_repair`, `fsck` — filesystem repair (use carefully)

### SRE checks

* If “disk full”: `df -h` → which mount? then `du -sh` to find culprit dir. Clean logs, rotated logs, temp files.
* If FS read-only: check `dmesg` for errors, then remount read-write after fix.

---

## 6 — Device I/O, drivers, interrupts

### Concepts

* **Drivers**: kernel modules that control hardware.
* **Interrupts**: device signals CPU that an event (I/O) happened.
* **DMA**: device copies directly to memory to reduce CPU use.
* **I/O Wait**: `%iowait` shows CPU waiting for I/O — key for disk bottlenecks.

### Commands

* `dmesg | less` — kernel messages (device errors)
* `lspci -vv` — PCI devices
* `iostat -x 1` (sysstat package) — per-device I/O stats (await, svctm, %util)
* `smartctl -a /dev/sdX` — SMART disk health
* `iotop` — per-process I/O

### SRE checks

* High latency: `iostat` shows high await and %util at or near 100% → disk bottleneck.
* Failed disk: `smartctl` failures + kernel logs → replace / rebuild RAID.

---

## 7 — Boot process & services

### Boot order (quick)

Firmware (BIOS/UEFI) → Bootloader (GRUB) → Kernel → init/systemd → units/targets/services.

### systemd commands

* `systemctl status <service>`
* `systemctl start/stop/restart <service>`
* `systemctl enable/disable <service>`
* `journalctl -u <service> --since "1 hour ago"`
* `journalctl -b` — logs since last boot

### SRE checks

* If service won’t start: `systemctl status` (exit code), then `journalctl -u` for logs. Check conf files, file permissions, port conflicts (`ss -lntp`), missing mounts, missing secrets.

---

## 8 — IPC, signals, deadlock (short)

* **IPC**: pipes, named pipes, sockets, shared memory, message queues.
* **Signals**: `SIGTERM` (graceful), `SIGHUP` (reload), `SIGKILL` (force).
* **Deadlock**: circular wait, hold-and-wait, no preemption, mutual exclusion — SREs should identify resource locking issues.

---

## 9 — Kernel vs user space, syscalls

* User apps call **syscalls** to request kernel services (read, write, open, socket).
* Kernel space = privileged, has hardware access; user space = isolated.

---

## 10 — Virtualization support in hardware

* Intel VT-x / AMD-V enable nested virtualization and efficient context switching for VMs. SREs should know when virtualization support is enabled/disabled (BIOS/UEFI setting) — check `egrep '(vmx|svm)' /proc/cpuinfo`.

---

# Linux (practical + exam-focused, deep)

We now focus on Linux specifics — commands, files, directories, services, security, and hands-on tasks.

## 1 — Filesystem layout (what to memorize quickly)

* `/` — root
* `/bin` — essential user binaries
* `/sbin` — system binaries (root)
* `/etc` — system configuration (e.g., `/etc/hosts`, `/etc/resolv.conf`, `/etc/passwd`)
* `/var` — variable data (logs: `/var/log`)
* `/home` — user home dirs
* `/proc` — kernel/procfs (live system info)
* `/sys` — sysfs (device/kernel interface)
* `/usr` — user programs
* `/boot` — kernel and bootloader files

Memorize a few: `/etc/hosts`, `/etc/resolv.conf`, `/var/log/syslog` or `/var/log/messages`, `/proc/cpuinfo`, `/proc/meminfo`.

## 2 — Common commands (cheat-list + one-liners)

### System / process

* `ps aux | grep <name>`
* `top` / `htop`
* `pgrep <name>` / `pkill <name>`

### Disk / FS

* `df -h`
* `du -sh /var/log/* | sort -h` — find big log files
* `lsblk`, `blkid`, `mount`

### Network

* `ip a` or `ifconfig`
* `ip route` (default gateway)
* `ss -tuln` or `netstat -tuln`
* `ping 8.8.8.8`, `ping google.com`
* `traceroute google.com`
* `dig google.com` / `nslookup`

### Logs & journal

* `journalctl -f` — follow live logs
* `journalctl -u <service> -n 200` — last 200 lines
* `dmesg | tail` — kernel messages

### Permissions & users

* `ls -l`
* `chmod 640 file`
* `chown user:group file`
* `useradd`, `passwd`, `usermod -aG group user`

### Package / updates

* Debian/Ubuntu: `apt update && apt install <pkg>`
* RHEL/CentOS: `yum install <pkg>` or `dnf install <pkg>`

### Quick health checks

* `uptime` (load average)
* `free -m`
* `vmstat 1 5`
* `iostat -x 1 3`

## 3 — Permissions: SUID/SGID/Sticky (common exam trap)

* Normal permissions: `-rwxr-xr--` (owner, group, others).
* **SUID** (setuid): executable runs with file owner’s privileges (e.g., passwd). Shown as `s` in user execute bit (`rws`).
* **SGID**: group s-bit; files run with group privileges, directories new files inherit group. Shown `rwxr-sr-x`.
* **Sticky bit** on directories (`t`): e.g., `/tmp` — only owner can delete their files.

## 4 — SELinux / AppArmor (brief)

* SELinux: mandatory access control; modes: `Enforcing`, `Permissive`, `Disabled`.
* Check: `sestatus` or `getenforce`.
* AppArmor provides profiles for confinement on Debian/Ubuntu.

## 5 — /proc and /sys (useful knowledge)

* `/proc` contains runtime kernel info:

  * `/proc/cpuinfo`, `/proc/meminfo`, `/proc/<pid>/status`, `/proc/<pid>/fd` (file descriptors).
* Useful in scripts to quickly fetch system info.

## 6 — systemd basics (deep enough for SRE)

* Units: service, target, timer, socket.
* `systemctl status multi-user.target`
* `systemctl list-units --type=service --state=failed` — find failed services.
* Timers can replace cron for modern setups.

## 7 — Log locations (know these)

* Systemd journal: `journalctl` (primary on modern distros).
* `/var/log/syslog` or `/var/log/messages` (distro-specific)
* `/var/log/auth.log` — auth events (login, sudo)
* `/var/log/kern.log` — kernel logs (some distros)

## 8 — Practical labs to run (do them, even quickly)

1. Create a file, change owner & perms:

```bash
sudo touch /tmp/testfile
sudo chown root:adm /tmp/testfile
sudo chmod 640 /tmp/testfile
ls -l /tmp/testfile
```

2. Find a process hog:

```bash
ps -eo pid,comm,%cpu,%mem --sort=-%cpu | head
top -b -n 1 | head -n 20
```

3. Simulate disk full (careful: do not run on real prod):

```bash
mkdir /tmp/fill && dd if=/dev/zero of=/tmp/fill/f bs=1M count=1024
# then check df -h and remove file to free
rm /tmp/fill/f && rmdir /tmp/fill
```

4. Follow logs while restarting service:

```bash
journalctl -fu nginx &
sudo systemctl restart nginx
# watch for errors in journalctl
```

## 9 — Troubleshooting flows (SRE approach + exact commands)

### Scenario A: “Service not starting after reboot”

1. `systemctl status svc` → read unit file and error summary.
2. `journalctl -u svc -n 200 --no-pager` → read errors.
3. `ss -lntp | grep <port>` → see if port is occupied.
4. `ls -l /path/to/config` → check ownership and perms.
5. `mount | grep /path` and `df -h` → ensure required mounts are present.
6. Fix config/permissions, then `systemctl restart svc`.

### Scenario B: “User can’t SSH”

1. Check client: `ssh -v user@host` (verbose).
2. On server: `ss -tuln | grep :22`, `journalctl -u sshd -n 200`.
3. Check `/etc/ssh/sshd_config` for `PermitRootLogin`/`PasswordAuthentication`.
4. Check `/etc/hosts.allow` or firewall `ufw status` / `iptables -L`.
5. Check disk/ inodes full (could prevent login): `df -h`, `df -i`.

### Scenario C: “VM slow / high iowait”

1. `top` → check `%wa`/`%iowait`.
2. `iostat -x 2` → look for high `await`, high `%util` on devices.
3. `iotop -ao` → find process with I/O.
4. `smartctl -a /dev/sdX` → check disk health.
5. If cloud: check underlying hypervisor logs (Prism/AHV metrics).

---

## 10 — Sample quiz questions (MCQ + short answers + scenarios) — **with model answers**

### MCQs (single best answer)

1. What does `df -h` show?

   * a) Memory usage by process
   * b) Disk usage by mount (human readable) ✅
   * c) CPU usage
   * d) Network routes

2. Which signal should you send for graceful shutdown?

   * a) SIGKILL
   * b) SIGTERM ✅
   * c) SIGSTOP
   * d) SIGSEGV

3. `chmod 4750 file` sets what?

   * a) SUID + rwx for owner, rx for group, none for others ✅ (SUID = 4 in first digit)

4. Which command shows kernel ring buffer messages?

   * a) `journalctl`
   * b) `dmesg` ✅
   * c) `syslog`
   * d) `top`

5. High `si/so` in `vmstat` means:

   * a) High CPU usage
   * b) Heavy swapping (I/O) ✅
   * c) High network traffic
   * d) File descriptor leak

### Short answer / definitions

1. Explain virtual memory in 2 lines.
   **Answer:** Virtual memory gives each process a private address space; the OS maps virtual pages to physical RAM and swap. It allows processes to use more memory than physical RAM via paging to disk.

2. What is the difference between process and thread?
   **Answer:** Processes have separate address spaces; threads share the same address space and are lighter-weight.

3. What file would you check to debug DNS settings on a Linux machine?
   **Answer:** `/etc/resolv.conf` (also `/etc/hosts` for local overrides).

### Scenario (short)

> A server reports “Out of memory” and a process `java` disappeared. What do you check and how to fix?
> **Model approach:** `dmesg | grep -i oom` to find OOM killer logs; `free -m` to check memory; `ps aux --sort=-%mem | head` to find other heavy processes; consider adding swap or memory, tune JVM Xmx, or kill memory-hungry processes. Add monitoring/alerts to detect before OOM.

---

## 11 — Common traps & examiner favorites (memorize)

* Where DNS config sits: `/etc/resolv.conf` and fallback `/etc/hosts`.
* `df` shows filesystem usage; `du` shows directory usage. Students confuse them.
* SUID/SGID bits shown as `s` in `ls -l`.
* `kill` vs `kill -9`: `kill` sends TERM; `-9` is forceful and bypasses cleanup.
* `systemctl status` is the first command for any service issue; always follow with `journalctl`.

---

## 12 — Quick study checklist (what to memorize right now)

* Commands: `ps`, `top`, `free`, `df`, `du`, `ss`/`netstat`, `journalctl`, `dmesg`, `mount`, `lsblk`, `iostat`, `iotop`.
* Files: `/etc/hosts`, `/etc/resolv.conf`, `/var/log/*`, `/proc/meminfo`, `/proc/cpuinfo`.
* Concepts: process lifecycle, OOM, swap, context switch, SUID/SGID, boot order, `systemd` basics.
* Troubleshooting steps for: service not starting, SSH failure, disk full, VM slow.

---

## 13 — 90-minute hands-on plan (do this once)

1. 0–15 min: Run `top`, `free -m`, `df -h`, `ip a`, `ss -tuln`, `journalctl -b | tail -n 50`.
2. 15–30 min: Create + change file ownership/perm; examine `ls -l`, `chmod`, `chown`.
3. 30–50 min: Simulate disk fill in a safe temp FS, then free it; view `df` and `du`.
4. 50–70 min: Check a service: `systemctl status` + `journalctl -u` and simulate a failing start by editing config (careful).
5. 70–90 min: Practice answering 10 MCQs and 3 scenario questions out loud.

---

## 14 — What to say in interview (framing answers)

* Always follow a structured approach: *Clarify → Gather data (commands/logs) → Hypothesize → Fix / Mitigate → Verify → Prevent (next steps)*.
* Use the words: “I would check `systemctl status` and `journalctl` first”, “I’d check `/etc/resolv.conf` for DNS”, “I’d look at `iostat` to confirm disk bottleneck”.

---

# Linux Command-Line Deep Lab — do these on a VM / WSL / cloud instance

(Use a disposable test VM. Many commands need `sudo` — don’t run destructive commands on production.)

## Quick setup (if needed)

```bash
# update & install helpful tools (Debian/Ubuntu)
sudo apt update
sudo apt install -y sysstat iotop dnsutils traceroute htop smartmontools
```

---

## Task 1 — Files & permissions (10–15 min)

Goal: master basic FS ops and permission bits.

Commands & steps:

```bash
pwd
ls -la
mkdir ~/lab_test
cd ~/lab_test
echo "hello nutanix" > file1.txt
cat file1.txt
touch exec.sh
chmod 750 exec.sh
ls -l
```

Check:

* `ls -l` shows permissions (`rwxr-x---`), owner and group.
* Set SUID example (understand only): `sudo chmod 4755 /usr/bin/passwd` (read output of `ls -l /usr/bin/passwd` — shows `-rwsr-xr-x`).

What to notice:

* `chmod 750` → owner rwx, group rx, others none.
* `chmod 4755` sets SUID (binary runs as owner).

---

## Task 2 — Processes & signals (10 min)

Goal: create a process, observe, kill, renice.

```bash
# spawn a sleeping process
sleep 300 &
sleep_pid=$!
ps -p $sleep_pid -o pid,cmd,stat
top -b -n 1 | head -n 12
# send TERM then KILL
kill $sleep_pid
# if still there:
kill -9 $sleep_pid
# create CPU load (danger: short)
yes > /dev/null &  # ctrl-c or kill after few seconds
pid=$(pgrep -n yes)
renice +10 -p $pid
ps -p $pid -o pid,ni,cmd,%cpu
kill -9 $pid
```

What to notice:

* `ps` shows status (S, R, D etc.). `stat` column codes.
* `kill` sends SIGTERM (allow graceful shutdown); `-9` is SIGKILL (force).
* `renice` changes niceness (higher = lower priority).

---

## Task 3 — Memory checks & OOM (10 min)

Goal: read memory state, find memory-heavy processes.

```bash
free -h
vmstat 1 5
cat /proc/meminfo | head
ps aux --sort=-%mem | head -n 10
```

Simulate memory stress carefully (optional):

```bash
# Danger: can freeze VM. Use small allocation:
python3 - <<'PY'
a=[]
for i in range(50):
    a.append('x'*10_000_000)   # ~10MB chunks
    print(i)
    import time; time.sleep(1)
PY
# then kill the python process if needed
```

What to notice:

* `free` shows total/free/swap.
* `vmstat` columns: `si`/`so` (swap in/out), `r` run queue, `free` memory.

---

## Task 4 — Disk usage & inodes (10 min)

Goal: find big files and check inodes.

```bash
df -h         # disk usage by filesystem
df -i         # inode usage
du -sh ~/* | sort -h | tail -n 10   # biggest dirs in home
# find big files (over 50MB)
find / -xdev -type f -size +50M -exec ls -lh {} \; 2>/dev/null | awk '{print $5,$9}' | head -n 20
```

Simulate filling a temp file (safe):

```bash
mkdir -p /tmp/labfill
dd if=/dev/zero of=/tmp/labfill/f1 bs=1M count=50   # creates 50MB file
df -h
rm /tmp/labfill/f1
rmdir /tmp/labfill
```

What to notice:

* `df` shows mount that’s full.
* `du` pinpoints directories.
* `df -i` warns if inodes exhausted (rare but exam trap).

---

## Task 5 — Services & logs (systemd) (10–15 min)

Goal: inspect service failures and journal.

```bash
# check a service (use one installed: e.g., ssh)
systemctl status ssh    # or sshd
journalctl -u ssh -n 50 --no-pager
# follow logs live
journalctl -f
# list failed units
systemctl --failed
```

If nginx installed:

```bash
sudo systemctl stop nginx
sudo systemctl start nginx
sudo systemctl restart nginx
journalctl -u nginx -n 80 --no-pager
```

What to notice:

* `systemctl status` gives brief failure + exit code.
* `journalctl -u` shows detailed logs and stack traces.

---

## Task 6 — Networking basics (10–15 min)

Goal: find IP, routes, open ports; test DNS and connectivity.

```bash
ip a
ip route
ss -tuln               # listening TCP/UDP ports
ping -c 4 8.8.8.8
ping -c 4 google.com
traceroute -m 15 google.com
dig google.com +short
# check DNS config
cat /etc/resolv.conf
# check hosts
cat /etc/hosts
```

What to notice:

* If ping IP works but name fails → DNS issue (`/etc/resolv.conf`).
* `ss -tuln` lists processes listening on ports (use `ss -tulpn` for PID if root).

---

## Task 7 — I/O & hardware checks (10 min)

Goal: check device I/O and health.

```bash
# if sysstat installed
iostat -x 1 3
# per-process I/O (iotop may need sudo)
sudo iotop -ao -k -b -n 3
# hardware PCI
lspci | head
# disk SMART (if run as root and device present)
sudo smartctl -a /dev/sda || true
```

What to notice:

* `iostat` columns: `await`, `%util` — high await + util near 100% = disk bottleneck.
* `iotop` shows processes generating I/O.

---

## Task 8 — Kernel messages & boot logs (5–10 min)

Goal: inspect kernel logs for hardware or OOM events.

```bash
dmesg | tail -n 40
journalctl -k -n 100
journalctl -b -0 | tail -n 100   # logs since boot
```

Look for:

* Disk errors, NIC link down messages, OOM-killer logs.

---

## Quick cheatsheet (memorize)

* Disk: `df -h`, `du -sh`, `lsblk`
* Memory: `free -m`, `vmstat 1`, `cat /proc/meminfo`
* Processes: `ps aux`, `top`, `pgrep`, `kill`
* Services/logs: `systemctl`, `journalctl -u <svc>`
* Network: `ip a`, `ip route`, `ss -tuln`, `ping`, `dig`
* I/O: `iostat -x`, `iotop`, `smartctl`

---

# 15-Question MCQ Drill — answer key & short explanations

1. `df -h` shows:
   A) Memory by process
   B) Disk usage by filesystem (human) ✅
   C) CPU usage
   D) Running processes
   **Why:** `df` = disk free; `-h` human readable.

2. Which signal allows graceful termination (can be trapped)?
   A) SIGKILL
   B) SIGSTOP
   C) SIGTERM ✅
   D) SIGSEGV
   **Why:** SIGTERM (15) can be caught to clean up; SIGKILL (9) cannot.

3. `ps aux | sort -nrk 3 | head` shows:
   A) Top memory consumers
   B) Top CPU consumers ✅
   C) Process tree
   D) Zombie processes
   **Why:** Sort by column 3 (%) CPU.

4. Which file typically holds DNS servers in Linux?
   A) /etc/hosts
   B) /etc/resolv.conf ✅
   C) /etc/network/interfaces
   D) /etc/dns.conf
   **Why:** `resolv.conf` lists nameservers.

5. `df -i` is used to check:
   A) Disk usage in gigabytes
   B) Inode usage ✅
   C) I/O stats
   D) Disk partitions
   **Why:** `-i` shows inode counts.

6. The permission string `-rwsr-xr-x` means:
   A) File is world-writable
   B) SUID bit is set ✅
   C) SGID bit is set
   D) Sticky bit is set
   **Why:** `s` in owner execute field indicates SUID.

7. High `si`/`so` in `vmstat` indicates:
   A) High CPU usage
   B) High swap activity (swap in/out) ✅
   C) High disk throughput only
   D) Network saturation
   **Why:** `si` = swap in, `so` = swap out.

8. `journalctl -u nginx -n 50` does:
   A) Shows last 50 lines from kernel log
   B) Shows last 50 lines of nginx unit logs ✅
   C) Restarts nginx
   D) Cleans nginx logs
   **Why:** `-u` selects the systemd unit.

9. Which command shows listening TCP sockets?
   A) netstat -tuln ✅
   B) df -h
   C) ip a
   D) ps aux
   **Why:** `-t` TCP, `-u` UDP, `-l` listening, `-n` numeric.

10. Inodes store:
    A) File content
    B) File metadata (owner, perms, pointers) ✅
    C) Block device names
    D) Mount points
    **Why:** Inode = metadata, not filename (directory maps names → inode).

11. `iostat` column `await` indicates:
    A) Network latency
    B) Average time (ms) for I/O request to be served ✅
    C) CPU wait for interrupts
    D) Swap time
    **Why:** `await` is avg I/O completion time.

12. If `systemctl status svc` shows exit code 203, you should next check:
    A) dmesg only
    B) journalctl -u svc ✅
    C) uptime
    D) df -h
    **Why:** `journalctl -u` gives full service logs and error traces.

13. Which of these is a user-space to kernel transition (syscall)?
    A) open() ✅
    B) fork()
    C) chmod
    D) ls
    **Why:** `open()` is a syscall; `fork()` also is one but option A is canonical. *(Either could be syscall; pick common example open())*

14. The OOM killer is invoked when:
    A) Disk space low
    B) Kernel runs out of memory to satisfy allocations ✅
    C) CPU 100%
    D) Swap disabled
    **Why:** OOM triggered when memory pressure is critical.

15. `ss -tulpn` run as root gives:
    A) Active processes only
    B) Listening sockets with PID/program name ✅
    C) Disk partitions
    D) Memory utilization
    **Why:** `ss -p` shows process, `-n` numeric.

---

# Score yourself quickly

* 13–15 right: very strong for quiz OS/Linux portion
* 10–12 right: good, review weak areas (permissions, iostat, vmstat)
* <10: re-run labs and memorize cheatsheet

---
# Topic 2 — Computer Networking (SRE-focused)

---

## 1) Big picture — Why networking matters for SRE

Networking is how services talk. Most customer problems (VM unreachable, slow app, DNS issues) are network-symptoms at first. As an SRE you must quickly isolate whether a problem is: host → network → or remote service.

---

## 2) OSI model (short & memorable)

Seven layers (bottom → top). Memorize order and a one-line job for each:

1. **Physical** — cables, bits, electrical/optical signals.
2. **Data Link** — MAC addresses, switches, frames, VLAN tags.
3. **Network** — IP addressing & routing (IPv4/IPv6).
4. **Transport** — TCP/UDP (end-to-end, ports, reliability).
5. **Session** — sessions/connection management (often merged with transport/app).
6. **Presentation** — encryption/serialization (TLS, encoding)
7. **Application** — HTTP, DNS, SSH, etc.

Interview tip: If asked to “explain OSI to a layperson”, say: “Physical = wires; Data Link = local switching (MACs); Network = IP routing between networks; Transport = ensures delivery (TCP) or fire-and-forget (UDP); Application = what users interact with (HTTP, DNS).”

---

## 3) TCP vs UDP (essentials)

* **TCP**: connection-oriented, reliable (ACKs, retransmits), ordered, congestion control, handshake (SYN/SYN-ACK/ACK). Use when correctness matters (HTTP, SSH).
* **UDP**: connectionless, no guarantees, low-latency. Use for DNS queries, VoIP, some streaming.

Common interview question: “When will you choose UDP over TCP?” — Answer: low-latency or tolerate packet loss (DNS queries, realtime audio), or when reliability is handled at app layer.

---

## 4) IPv4, IPv6 & Subnetting — step-by-step (you must be able to *calculate*)

### CIDR & masks

* Notation: `192.168.1.0/24` means first 24 bits are network mask = `255.255.255.0`.
* Number of addresses in a prefix: `2^(32 - prefix)`. Usable hosts = `2^(32 - prefix) - 2` (subtract network & broadcast) — exceptions: `/31` (2-address for point-to-point), `/32` (single host).

### Example — compute with exact steps (Important: do this in interviews)

Calculate network & usable range for IP `192.168.10.70/26`:

1. /26 → mask = 255.255.255.192. Last octet mask bits = `11000000` => block size = `256 - 192 = 64`.
2. Network blocks start at .0, .64, .128... Find which block contains 70 → `.64`. So:

   * **Network address** = `192.168.10.64`
   * **Broadcast** = `192.168.10.127` (`network + block_size - 1`)
   * **Usable hosts** = `192.168.10.65` → `192.168.10.126` (62 hosts).
3. Formula check: `2^(32-26) = 2^6 = 64` total addresses → usable = 62.

Memorize common prefixes: `/24 = 256 addr (254 usable)`, `/25 = 128 (126)`, `/26 = 64 (62)`, `/30 = 4 (2 usable, common for links)`.

### IPv6 basics (short)

* Big address space, written as hex groups. Example prefix: `2001:db8::/32`. `/64` commonly used for subnets. Use `ip -6 addr` to view IPv6.

---

## 5) Common network devices & functions

* **Switch (L2)**: forwards frames using MAC; supports VLANs.
* **Router (L3)**: forwards packets between IP networks; runs routing protocols.
* **Firewall**: filters traffic by rules (stateful).
* **Load balancer**: distributes traffic via algorithms (round-robin, least-conns).
* **NIC**: network interface card. Bonding/teaming gives redundancy and/or throughput.

---

## 6) Important ports & protocols you should memorize

* SSH: TCP 22
* HTTP: TCP 80, HTTPS: TCP 443
* DNS: UDP 53 (queries), TCP 53 (zone transfers/large responses)
* DHCP: UDP 67/68
* NTP: UDP 123
* ICMP: used for ping (not a TCP/UDP port)

Interview tip: know that DNS queries typically use UDP and fall back to TCP if response too large.

---

## 7) Tools & commands (practical; prefer `ip` over `ifconfig`)

Use these daily on Linux:

* `ip a` — show interfaces & IPs
* `ip link` — interface state
* `ip route` — routing table / default gateway
* `ss -tuln` or `netstat -tuln` — listening sockets
* `ping <ip|host>` — basic reachability
* `traceroute <host>` (or `tracepath`) — route path & hops
* `dig <domain> +short` or `nslookup` — DNS queries
* `tcpdump -n -i eth0 host 1.2.3.4` — capture packets (requires sudo)
* `arp -n` — ARP table
* `ethtool eth0` — NIC details & link speed
* `curl -I http://host` — check HTTP headers/status
* `nc -zv host port` or `telnet host port` — test TCP port connectivity

Memorize `ss` flags: `-t` TCP, `-u` UDP, `-l` listening, `-n` numeric, `-p` show PID.

---

## 8) Troubleshooting flows (step-by-step SRE style)

### Scenario A — “Can’t reach google.com”

1. Check local network stack: `ip a` (interface up, IP assigned?)
2. Check route: `ip route` (is default gateway present?)
3. Test remote by IP: `ping 8.8.8.8`.

   * If ping IP works, name resolution issue → `dig google.com` and `cat /etc/resolv.conf`
   * If ping IP fails → check gateway/router: `traceroute 8.8.8.8` to see where it drops.
4. Check firewall `iptables -L` or `ufw status` if host blocks traffic.
5. If DHCP-managed IP, verify lease `dhclient` logs or `journalctl -u NetworkManager`.

### Scenario B — “Service port not listening from client”

1. From server: `ss -tuln | grep :PORT` to verify service listening.
2. If not listening: check `systemctl status` and service logs.
3. If listening on 127.0.0.1 only, config binds to loopback — fix to 0.0.0.0.
4. If listening but blocked: check host firewall, security groups (cloud), or network ACLs.

### Scenario C — “High latency / packet loss”

1. `ping -c 20 host` to measure packet loss and RTT range.
2. `traceroute` to identify hop where RTT increases.
3. `mtr host` (if allowed) for continuous path + loss view.
4. Capture around the problem: `tcpdump -w trace.pcap host x.x.x.x` then analyze in Wireshark.
5. If NIC or switch: `ethtool eth0` for link errors, `dmesg` for kernel NIC errors.

---

## 9) Subnetting practice — 2 examples (show work)

### Example 1

Question: Given IP `10.1.5.200/24` — network address? usable range?

* /24 mask = 255.255.255.0 → network = `10.1.5.0`. Usable hosts: `10.1.5.1` → `10.1.5.254`. Broadcast `10.1.5.255`.

### Example 2 (bitwise blocks)

Question: `172.16.30.77/27`:

* /27 → mask 255.255.255.224 → block size 32 (256 - 224 = 32). Networks: .0, .32, .64, .96...
* 77 falls in .64 block → **network** `172.16.30.64`, **broadcast** `172.16.30.95`, usable `172.16.30.65` → `172.16.30.94`. Hosts = 30.

Be explicit in interviews: state mask, block size, list block boundaries, find the block containing the IP.

---

## 10) DNS details SREs must know

* Resolver config: `/etc/resolv.conf` lists nameservers.
* DNS resolution order: `/etc/hosts` → DNS server(s).
* `dig +short google.com` shows resolved IPs.
* `dig @8.8.8.8 google.com` queries a specific server.
* DNS types: A (IPv4), AAAA (IPv6), CNAME (alias), MX (mail), PTR (reverse).
* DNS uses UDP/53 for queries; if response >512 bytes (or EDNS0), it may use TCP/53.

Interview question: “Why would DNS lookup succeed from one machine but not another?” — check `/etc/resolv.conf`, firewall, or split-horizon DNS (different views for internal/external).

---

## 11) VLANs, trunking, and NIC bonding (short)

* **VLAN**: virtual LAN segregates L2 broadcast domains. VLAN tag (802.1Q) adds a VLAN ID.
* **Trunk port**: carries multiple VLANs between switches.
* **Bonding/teaming**: link aggregation for redundancy/throughput (modes: active-backup, 802.3ad LACP).

SRE practical: know how to check VLANs on Linux (look at `ip -d link`, `bridge` commands) and bonding (`cat /proc/net/bonding/bond0`).

---

## 12) Hands-on Networking Lab (do these now on your VM)

Run these in order:

1. Show interface + IPs

```bash
ip a
```

2. Show default gateway

```bash
ip route
```

3. Test raw connectivity

```bash
ping -c 4 8.8.8.8
```

4. Test DNS

```bash
dig +short google.com
cat /etc/resolv.conf
```

5. Check listening ports and processes

```bash
ss -tuln
ss -tulpn   # as root to see PIDs
```

6. Trace path to a host

```bash
traceroute -m 20 google.com
# or
tracepath google.com
```

7. Test a TCP port (example port 80)

```bash
nc -zv google.com 80   # or telnet google.com 80
```

8. Capture a tiny TCP handshake (requires sudo)

```bash
sudo tcpdump -i any -n host google.com and port 80 -c 20 -w /tmp/trace.pcap
# then read with: sudo tcpdump -r /tmp/trace.pcap -n -tt
```

---

## 13) Sample interview questions & model answers

1. **Q:** Explain the OSI model layers.
   **A:** (Recite layers + one-line purpose each; relate to problem — e.g., if you can’t ping an IP, start at Layer 1).

2. **Q:** How does DNS resolution work when you type `example.com`?
   **A:** Browser → OS checks `/etc/hosts` → resolver sends UDP 53 to configured nameserver → recursive resolver → authoritative server → returns A/AAAA → browser connects via TCP to port 80/443; if packet >512 bytes or EDNS, may use TCP.

3. **Q:** `ping google.com` fails but `ping 8.8.8.8` succeeds — what now?
   **A:** Check DNS: `/etc/resolv.conf`, `dig google.com`; check firewall blocking DNS (UDP 53); check local hosts file.

4. **Q:** How to find which process is listening on TCP port 8080?
   **A:** `ss -tulpn | grep 8080` (or `sudo lsof -i :8080`).

5. **Q:** How would you explain traceroute output spike at hop 4?
   **A:** Possibly that hop has high latency or it's deprioritizing ICMP responses. Check multiple traceroutes, use `mtr` for trend.

6. **Q:** What’s APIPA (169.254.x.x)?
   **A:** Automatic Private IP Addressing — host assigns private link-local address when DHCP fails.

7. **Q:** How does HTTPS differ from HTTP?
   **A:** HTTPS uses TLS to encrypt the HTTP payload; TLS handshake happens before HTTP data; certificates verify server identity.

8. **Q:** How to check MTU on interface?
   **A:** `ip link show dev eth0` (shows mtu) or `ping -M do -s <size> host` for fragmentation testing.

---

## 14) 15-question MCQ drill — answer key + brief explanations

1. The OSI layer responsible for IP addresses and routing is:
   A) Data Link B) Network ✅ C) Transport D) Session
   *Why:* Network = IP routing.

2. Which protocol is connectionless and does not guarantee delivery?
   A) TCP B) UDP ✅ C) HTTP D) TLS
   *Why:* UDP is connectionless.

3. `192.168.1.10/28` has how many usable hosts?
   A) 14 ✅ B) 30 C) 62 D) 254
   *Why:* /28 → 16 addresses → usable = 14.

4. Which command shows listening TCP/UDP sockets with PIDs?
   A) ip a B) ss -tulpn ✅ C) df -h D) ps aux
   *Why:* `ss -tulpn` shows process names & ports.

5. DNS primary query protocol is:
   A) TCP 53 B) UDP 53 ✅ C) ICMP D) HTTP
   *Why:* DNS queries normally use UDP.

6. If `ping 8.8.8.8` works but `dig google.com` fails, cause is likely:
   A) Routing B) DNS ✅ C) Firewall D) ARP
   *Why:* IP reachable but name lookup fails.

7. A `/30` subnet on IPv4 provides how many usable hosts?
   A) 0 B) 1 C) 2 ✅ D) 4
   *Why:* /30 → 4 addresses → 2 usable (used for point-to-point links).

8. Traceroute uses which packet type by default on Linux?
   A) TCP B) ICMP (UDP probes historically) ✅
   *Why:* Traditional traceroute uses UDP probes (some OS use ICMP/TCP). (Accept explanation.)

9. What is the default gateway?
   A) DNS server B) Next-hop for non-local addresses ✅ C) Broadcast address D) DHCP server
   *Why:* Router used when destination not in local subnet.

10. `netstat -rn` vs `ip route` — which shows routing table?
    A) netstat -rn and ip route ✅ B) only netstat C) only ip route D) neither
    *Why:* Both show routing table.

11. NTP uses which port?
    A) UDP 53 B) TCP 123 C) UDP 123 ✅ D) TCP 80
    *Why:* NTP uses UDP 123.

12. What does ARP do?
    A) Map IP → MAC ✅ B) Map domain → IP C) Map MAC → IP D) Encrypt packets
    *Why:* ARP resolves IPv4 addresses to MAC addresses in L2.

13. Which command can capture packets for analysis?
    A) tcpdump ✅ B) traceroute C) ping D) ss
    *Why:* tcpdump captures packets.

14. Which is true about VLANs?
    A) They combine multiple IP subnets into one broadcast domain. B) They separate broadcast domains logically over same physical switch ✅ C) VLANs operate at L3. D) VLAN tags are removed by hosts.
    *Why:* VLANs segment L2 domains logically.

15. If a server’s NIC shows many RX errors, which is a likely cause?
    A) Misconfigured DNS B) Physical cable/link issues or duplex mismatch ✅ C) Incorrect subnet mask D) High CPU usage
    *Why:* RX errors often due to physical link problems.

Score guide: 13–15 very good; 10–12 ok; <10 review labs.

---

## 15) Short memorization checklist (for quick pre-test revision)

* OSI layers order and one-line purpose each.
* TCP vs UDP differences & use-cases.
* Common ports: 22, 80, 443, 53, 123.
* `ip a`, `ip route`, `ss -tuln`, `dig`, `traceroute`, `tcpdump` commands.
* Subnet calculation method: mask → block size → find network block → broadcast → usable range. Memorize /24, /25, /26, /30 values.
* DNS resolution steps & `/etc/resolv.conf` vs `/etc/hosts`.
* Basic troubleshooting checklist: Interface → Route → Reachability (IP) → Name resolution → Firewall.

---

## 16) 60-minute practice plan (do this once)

1. 0–10 min: Run `ip a`, `ip route`, `ss -tuln`, `cat /etc/resolv.conf`.
2. 10–25 min: `ping 8.8.8.8`, `ping google.com`, `dig google.com`, `traceroute google.com`.
3. 25–40 min: Simulate local port issue (run a simple server: `python3 -m http.server 8080`), then from another shell `ss -tuln` & `nc -zv localhost 8080`. Bind to loopback to see difference.
4. 40–55 min: Subnetting drills (3 problems: /26, /27, /30) solving step-by-step on paper/terminal.
5. 55–60 min: Quick MCQ drill above.

---

Great — next topic: **Virtualization & Cloud (with Nutanix-focused HCI / AHV / Prism details)**. I’ll teach this from the ground up, with clear concepts, practical checks, SRE troubleshooting flows, hands-on tasks, and quiz questions — all tuned to what Nutanix expects.

I used official Nutanix docs & high-quality references for the Nutanix-specific parts (links listed inline where it matters).

---

# Virtualization & Cloud — Deep, SRE-focused

## 1) Big idea — what virtualization gives you (one-liner)

Virtualization lets one physical machine run **multiple isolated guest systems (VMs)** by abstracting hardware through a hypervisor. This enables density, failover, snapshots, live migration and easier operations.

---

## 2) Hypervisors — types & where they run

* **Type-1 (bare-metal)**: runs directly on hardware (no host OS). Used in datacenters — examples: VMware ESXi, Nutanix AHV, Microsoft Hyper-V. Enterprise SREs deal mostly with Type-1.
* **Type-2 (hosted)**: runs on top of a host OS (e.g., VirtualBox, VMware Workstation) — mostly for dev/test.

Why it matters for SRE: Type-1 has lower overhead, tighter integration with storage/networking, and richer HA features — these affect troubleshooting steps and where to look for problems.

---

## 3) VMs vs Containers (short, practical)

* **VMs**: full OS, separate kernel, stronger isolation, heavier (longer boot, more resource). Good for multi-tenant and legacy apps.
* **Containers** (Docker, Kubernetes): share host kernel (namespaces/cgroups), lightweight, fast startup; ideal for microservices.
  SRE takeaway: containers are for app-level packaging; VMs provide full OS control and are common on Nutanix AHV.

---

## 4) Resource allocation & overcommit

* CPU & memory overcommit means more virtual resources allocated than physical available — hypervisor schedules/balloons memory and throttles CPU.
* **Ballooning**: hypervisor module asks guest to free memory (guest driver) to handle host memory pressure.
* Impact: overcommit can cause performance issues (swap, high CPU ready/wait). Monitor hypervisor metrics (e.g., %CPU ready) and guest metrics.

---

## 5) HCI (Hyperconverged Infrastructure) — what it is (Nutanix context)

HCI combines compute, storage, and networking into a **distributed software layer** running on commodity servers; data/services are sharded and pooled across nodes — no separate SAN required. Nutanix positions HCI to simplify scaling and operations. ([Nutanix][1])

Why SREs care: troubleshooting moves from single-host view to cluster view — e.g., local disk IO may be served by CVMs on other nodes in the cluster.

---

## 6) Nutanix architecture essentials (CVM, AHV, Prism)

### Controller VM (CVM)

* Each Nutanix node runs a special VM called a **Controller VM (CVM)** that provides the Nutanix distributed storage and management services. The CVM serves I/O for VMs on its node and participates in cluster services. If a CVM fails, other CVMs continue serving IO for resilience. ([portal.nutanix.com][2], [NutanixBible.com][3])

### AHV (Acropolis Hypervisor)

* **AHV** is Nutanix’s native Type-1 hypervisor (KVM-based) integrated into the HCI stack. It supports enterprise features such as live migration, high availability, and one-click full-stack upgrades. Live migration moves running VMs between hosts without downtime. ([portal.nutanix.com][4])

### Prism (Management plane)

* **Prism** is Nutanix’s UI/control plane for monitoring & managing clusters; **Prism Central** manages multiple clusters centrally. Prism provides dashboards, health, capacity, one-click ops and troubleshooting views. Use Prism first for cluster-level visibility. ([Nutanix][5], [portal.nutanix.com][6])

### CLIs: aCLI & nCLI

* Nutanix provides command-line tools (aCLI for Acropolis/AHV and nCLI for AOS) typically run from within the CVM for management automation and deep troubleshooting. Docs and command refs are on the Nutanix portal. ([portal.nutanix.com][7])

---

## 7) Common virtualization features you must know (SRE lens)

* **Live migration**: move a running VM to another host (no downtime) — useful for maintenance or load balancing. ([portal.nutanix.com][4])
* **High Availability (HA)**: restart VMs on other hosts if host fails.
* **Snapshots & Clones**: quick point-in-time copies (useful for rollbacks).
* **Distributed storage services**: dedupe/compression/erasure coding perform at the cluster level (often via CVM). ([NutanixBible.com][3])
* **One-click upgrades**: Nutanix promotes one-click full-stack upgrades (firmware + hypervisor + software). ([portal.nutanix.com][4])

---

## 8) Troubleshooting virtualization issues — stepwise SRE playbook

### Scenario A: VM won’t boot

1. **Check VM state** in Prism (is it powered on, paused, error?).
2. If powered but guest OS not responsive: check **console** (Prism console or VNC) for kernel panic or bootloader errors.
3. **Check storage**: does the VM’s vdisk show IO errors? Look at CVM storage alerts (Prism) and kernel logs on CVM.
4. Check host health: `host` may be in maintenance or have hardware issues — review host sensors and `ipmitool` / BMC logs if available.
5. If necessary, attach rescue ISO or boot into single-user mode.

### Scenario B: VM migration failed / stuck

1. Check network connectivity between source and dest host (MTU mismatches can break migration).
2. Check migrations logs in Prism and CVM logs.
3. Look for storage latency on source/destination (high I/O await can block live migration).
4. Use `acli`/`ncli` on the CVM to inspect VM and migration state (see docs). ([portal.nutanix.com][7])

### Scenario C: Storage performance issue (high latency)

1. Check VM metrics in Prism (IOPS, latency, read/write mix).
2. Check per-node storage metrics on CVM (disk utilization, SSD/HDD health via SMART).
3. Use `iostat`/`iotop` on CVM for local device stats.
4. Consider data locality (is the VM’s data remote?) and rebuild/dedup/ERASURE coding activity.

### Scenario D: Node/CVM offline

1. Prism will show node offline. Check whether CVM is down or the hypervisor is down.
2. SSH into other CVMs in the cluster and check cluster health (`ncli` / `acli` or Prism).
3. If a node is unreachable, network or hardware issue may be the cause — check switch logs, IPMI, and NICs.

---

## 9) Useful commands & places to look (what to run as SRE)

> **At cluster level (use Prism UI first)** — look at health dashboard, alerts, performance charts, recent events. ([Nutanix][5])

> **On a Controller VM (CVM)** (SSH into CVM):

* `acli` — Acropolis CLI (manage VMs on AHV). Docs: Nutanix aCLI reference. ([portal.nutanix.com][7])
* `ncli` — Nutanix CLI for AOS-level operations. ([portal.nutanix.com][8])
* Check cluster status: use `cluster status` or relevant nCLI/acli commands (see docs).
* Use standard Linux tools on CVM: `dmesg`, `iostat`, `top`, `journalctl` for local logs.
* Inspect VM processes with `virsh list`/**if** KVM/libvirt present (AHV is KVM-based; actual management is via Nutanix tooling).

> **From hypervisor host (if you have shell access)**: `virsh list`, `virsh dominfo <vm>`, `xm`/`qemu` tooling depends on hypervisor. (Primary interface for Nutanix is Prism/acli.)

**Note:** exact command names and flags change by AOS/AHV version — use Nutanix docs for version-specific syntax. ([portal.nutanix.com][7])

---

## 10) Hands-on lab ideas (what to practice locally)

You may not have Nutanix hardware — practice these on a laptop/VM:

1. **KVM/libvirt lab** (local):

   * Install `virt-manager`/`libvirt` on Linux. Create a VM, `virsh list`, `virsh migrate` local migration to another host if you have two hosts or experiment with live snapshot.
   * Practice `virt-top`, `virsh dominfo <vm>`, `virsh console <vm>`.

2. **Docker/Kubernetes mini-lab**:

   * Run `docker run -d --name web nginx` and test container restart behavior; compare with VM lifecycle.
   * If you know minikube, deploy a small pod and test killing node/eviction behavior.

3. **Simulate storage latency**:

   * On a VM, run `dd if=/dev/zero of=/tmp/testfile bs=1M count=200` while `iostat` runs to watch disk load.

4. **Practice CLI skills**:

   * Read Nutanix aCLI docs and memorize common verbs: list VMs, get VM details, start/stop VM. (Use docs.) ([portal.nutanix.com][7])

---

## 11) Nutanix-specific quick facts to memorize (for quiz / interview)

* A Nutanix cluster runs a special **Controller VM (CVM)** on each node which handles distributed storage and control services. ([portal.nutanix.com][2], [NutanixBible.com][3])
* **AHV** is Nutanix’s native hypervisor (KVM-based) integrated with the HCI stack; it supports live migration and HA. ([portal.nutanix.com][4])
* **Prism** is the single-pane management UI; **Prism Central** manages multiple clusters. ([Nutanix][5], [portal.nutanix.com][6])
* Management CLIs: **aCLI** (Acropolis CLI for AHV) and **nCLI** (AOS CLI) — run on the CVM. ([portal.nutanix.com][7])

(These are high-value facts interviewers like to hear.)

---

## 12) Sample quiz questions (with model answers)

**MCQ / short**

1. What is the Nutanix Controller VM (CVM)?
   **Answer:** A special VM on every node running Nutanix services and serving I/O for local VMs. ([NutanixBible.com][3])

2. Which hypervisor type is AHV?
   **Answer:** Type-1 (bare-metal KVM-based hypervisor). ([portal.nutanix.com][9])

3. What command/tool would you use first to see cluster health in Nutanix?
   **Answer:** Prism (UI) — then CLI (aCLI/nCLI) for deep dives. ([Nutanix][5], [portal.nutanix.com][7])

4. True/False: In Nutanix, if a CVM fails, the node becomes unusable.
   **Answer:** False — other CVMs can serve I/O and cluster continues operating, though capacity/perf may be impacted. ([portal.nutanix.com][2])

**Scenario**

> A VM is extremely slow (high latency). You see high disk `await` on node A in Prism. What steps do you take?
> **Model answer (concise):** Check per-VM IOPS/latency in Prism, identify offending VMs with high IO, inspect CVM disk stats (iostat/smartctl), check for background activities (resync/replication), check network (MTU & link errors), consider moving noisy VMs or increasing local resources, and open an incident if hardware shows SMART errors.

---

## 13) Interview framing tips (what to say)

* When asked about Nutanix: start with HCI definition → CVM role → Prism for management → AHV features (live migration, HA) — concise 2–3 sentences and show you know which layer to check first when problems occur (Prism → CVM → hypervisor → host hardware). ([Nutanix][1], [NutanixBible.com][3])
* Emphasize **customer focus**: e.g., “I’d check Prism for cluster health and customer-impacting metrics, then follow logs on CVM, then inspect hypervisor/host.”

---

## 14) Quick study checklist (what to memorize right now)

* HCI concept & benefits. ([Nutanix][1])
* CVM role & that it runs on every node. ([portal.nutanix.com][2], [NutanixBible.com][3])
* Prism = first look for monitoring; Prism Central manages many clusters. ([Nutanix][5], [portal.nutanix.com][6])
* AHV features: live migration, HA, one-click upgrades. ([portal.nutanix.com][4])
* aCLI / nCLI are Nutanix CLIs used on CVM. ([portal.nutanix.com][7])

---
# Topic: Hardware & Storage — Deep, SRE-Focused (Nutanix-ready)

## 1 — Big picture (one line)

Storage + hardware determine performance and reliability: SREs must distinguish **capacity** problems from **latency/IOPS** problems and identify hardware faults quickly.

---

## 2 — Key components and why they matter

* **CPU / Cores / Threads / Sockets** — influence compute capacity, VM density, scheduling.
* **Memory (RAM, ECC)** — affects caching and stability; ECC detects/corrects bit flips (important in datacenters).
* **NICs / Network fabric** — link speed, duplex, errors, offloads (TSO, GRO) affect throughput and latency.
* **Disk types**:

  * **HDD (spinning)** — high capacity, higher latency, low random IOPS (\~50–200 IOPS typical). Good for sequential throughput.
  * **SSD (NAND, SATA/SAS/NVMe)** — low latency, high IOPS (thousands to 100k+ depending on NVMe). NVMe > SAS/SATA.
* **Controllers / RAID / JBOD** — RAID provides redundancy/performance depending on level; JBOD exposes raw disks.
* **PSU / Fans / Motherboard / BMC (IPMI)** — hardware health sensors accessible via IPMI/Redfish.
* **Storage network / Fabric** — iSCSI, Fibre Channel, NVMe-oF; matters for SAN performance.

---

## 3 — Storage performance concepts (must know)

* **IOPS (I/O operations per second)** — how many reads/writes per second a device can handle (random I/O).
* **Throughput (MB/s)** — how many megabytes per second (sequential workloads).
* **Latency (ms or µs)** — time to complete an I/O. For SRE, latency is often the key metric: high latency → poor app performance.
* **Read vs Write patterns** — writes can be more expensive (write amplification, parity updates).
* **Queue depth** — concurrent outstanding I/O requests; affects throughput and latency.
* **Block size** — small random 4K reads stress IOPS; large 1MB sequential reads stress throughput.

Quick rule-of-thumb: HDD IOPS ≈ 100; consumer SATA SSD tens of thousands random IOPS; NVMe enterprise SSDs 100k+ IOPS (ranges vary).

---

## 4 — RAID basics (what to memorize + SRE implications)

* **RAID 0 (striping)** — performance up; no redundancy. (Use only when performance > safety.)
* **RAID 1 (mirror)** — two copies, good read performance, write overhead ×2 for capacity. 50% usable.
* **RAID 5 (striping + parity)** — N-1 capacity usable, good read, slower writes (parity calc); single-disk fault tolerance. Write penalty ≈ 4 IOPS (reads+parity).
* **RAID 6 (double parity)** — tolerate two disk failures; higher write penalty than RAID5.
* **RAID 10 (1+0)** — stripe across mirrors: good read/write, faster rebuilds, \~50% capacity like RAID1 but better performance.

**SRE notes:** RAID rebuilds place heavy load on remaining disks → system performance may degrade. For large drives, rebuild times are long; erasure coding (used in distributed storage) can mitigate but has its own cost.

---

## 5 — SAN vs NAS vs Local (block vs file vs object)

* **Block storage (iSCSI, FC)** — raw block device presented to host; filesystem created by OS. Good for databases.
* **File storage (NFS, SMB)** — exported filesystem mounted by clients. Easier sharing.
* **Object storage (S3)** — metadata-rich, HTTP APIs; good for large scale, immutable objects.

**Nutanix note:** Nutanix HCI uses local disks aggregated by software (CVMs) to create a distributed block/file system — so failure modes differ from a central SAN.

---

## 6 — Important commands & tools (Linux & SRE)

* `lsblk` — list block devices
* `blkid` — filesystem/UUID info
* `fdisk -l` or `parted -l` — partitions (careful, read-only usage)
* `smartctl -a /dev/sdX` — SMART health for drives (look at Reallocated\_Sector\_Ct, Reallocated\_Event\_Count, Pending\_Sector)
* `hdparm -I /dev/sdX` — device identification (use with care)
* `iostat -x 1` — per-device utilization, await, svctm, %util
* `iotop` — per-process I/O (sudo)
* `fio` — synthetic I/O benchmarking (use carefully on test systems)
* `mdadm --detail /dev/md0` — manage Linux software RAID (if used)
* `multipath -ll` — multipath LUNs
* `lsraid`/vendor tools — hardware RAID controller utilities (MegaCli, storcli)
* `ipmitool sdr` or `ipmitool sel list` — hardware sensors and logs (from BMC)
* `smartctl -t short /dev/sdX` — run short self-test.

---

## 7 — Detecting and diagnosing hardware failures (playbook)

### Symptom: higher latency / slow I/O

1. Check host-level IO: `iostat -x 1 3` → look at `await`, `%util`.
2. On host: `iotop` to find process causing heavy I/O.
3. Check device SMART: `sudo smartctl -a /dev/sdX`. Important attributes: Reallocated\_Sector\_Ct, Current\_Pending\_Sector, Offline\_Uncorrectable.
4. Check kernel logs: `dmesg | egrep -i 'sd|ata|error|fail|link'` or `journalctl -k`.
5. If multipath: `multipath -ll` to see path failures.
6. If RAID controller: vendor CLI logs (storcli/MegaCli) for failed physical disk.

### Symptom: disk gone / array degraded

1. Identify failed disk via `mdadm --detail` or controller CLI.
2. Check SMART and physical LED (if in datacenter).
3. Replace disk per vendor/Nutanix instructions (often `remove` then `add` via CLI).
4. Monitor rebuild/rebalance and throttling settings.

**Nutanix specifics:** In HCI, you’d check Prism → Alerts → Disk/Node health, then follow Nutanix KB for replacement. CVM logs and `ncli`/`acli` show device state.

---

## 8 — Practical labs (safe guidance)

> **Do these on a test VM or non-prod host only.**
> Avoid destructive operations on production. Use read-only commands first.

1. **List block devices**

```bash
lsblk -o NAME,SIZE,RO,TYPE,MOUNTPOINT
blkid
```

2. **Check SMART health**

```bash
sudo smartctl -a /dev/sda | egrep -i 'model|serial|user|temp|reallocated|pending|error'
```

3. **Observe disk I/O**

```bash
sudo iostat -x 1 5
sudo iotop -ao -b -n 5   # requires sudo
```

4. **Check multipath (if present)**

```bash
sudo multipath -ll
```

5. **Run a conservative fio test** (small, non-damaging)

```bash
# 4k random read test, 8 jobs, 10s
fio --name=test --rw=randread --bs=4k --size=100M --numjobs=8 --runtime=10 --time_based --group_reporting
```

6. **View BMC sensors (if access)**

```bash
sudo ipmitool sdr
sudo ipmitool sel list
```

---

## 9 — SRE troubleshooting scenarios (model answers)

### Scenario 1 — “Customers report some VMs are slow with high storage latency”

1. Check Prism/cluster dashboards (if Nutanix) for host/VM IOPS & latency.
2. On affected host/CVM: `iostat -x`, `iotop` to find hot devices/processes.
3. Check for background activities: rebuild, compaction, erasure-coding, snapshots, backups. These can spike IO.
4. Check SMART attributes for failing drives.
5. Consider temporarily throttling background jobs or migrating noisy VMs.

### Scenario 2 — “A disk has failed, array degraded”

1. Identify failed disk (controller CLI / mdadm / Prism).
2. Confirm failure via SMART and kernel logs.
3. Schedule replacement; follow platform-specific steps (hot-swap if supported). For Nutanix: follow KB for safe disk replacement to avoid data loss.
4. Monitor rebuild: expect performance impact — inform customers and mitigate (move VMs if needed).

### Scenario 3 — “Intermittent network packet drops to storage target”

1. Check NIC stats: `ethtool -S ethX`, `ifconfig` (RX/TX errors) or `ip -s link`.
2. Check switch port counters and flow (duplex mismatch causes errors).
3. Check multipath path health and failover events.
4. If using RDMA/NVMe-oF, check fabric health (latency, congestion).

---

## 10 — Common exam/test Qs & model answers

**Q:** HDD vs SSD — 3 differences.
**A:** SSD has much lower latency, higher random IOPS, no moving parts (less mechanical failure modes), more expensive per GB; HDD offers large capacity at lower cost, better sequential throughput per dollar.

**Q:** RAID 5 vs RAID 10 — when choose which?
**A:** RAID 5 gives space efficiency (usable N-1 disks) but has slower writes and single-disk fault tolerance; RAID 10 (striped mirrors) gives higher write performance and faster rebuilds with better redundancy—choose RAID10 for DBs with heavy write/low-latency needs.

**Q:** What SMART attributes matter for failure prediction?
**A:** Reallocated\_Sector\_Ct, Current\_Pending\_Sector, Offline\_Uncorrectable, SMART overall-health, Raw\_Read\_Error\_Rate — watch increases/threshold crossings.

**Q:** How to calculate IOPS from latency?
**A:** Approx: IOPS ≈ 1000 / avg\_latency\_ms (for single outstanding IO). At queue depth >1, IOPS = queue\_depth / avg\_latency\_ms \* 1000. (Use with caution — measurement depends on workload and concurrency.)

**Q:** What does high `await` in `iostat` mean?
**A:** Average time (ms) for I/O requests to be served — high await = high device latency.

---

## 11 — 12-question MCQ drill (answers included)

1. Which disk type typically has the **lowest random read latency**?
   A) HDD B) SATA SSD C) NVMe SSD ✅ D) Tape
   **Ans:** C

2. RAID level giving tolerance to **two simultaneous disk failures**:
   A) RAID 1 B) RAID 5 C) RAID 6 ✅ D) RAID 0
   **Ans:** C

3. SMART attribute indicating reallocated sectors:
   A) Reallocated\_Event\_Count B) Reallocated\_Sector\_Ct ✅ C) Pending\_Sector\_Count D) Spin\_Retry\_Count
   **Ans:** B

4. `iostat` column showing average I/O service time per request:
   A) tps B) await ✅ C) r/s D) util
   **Ans:** B

5. Which tool gives per-process I/O usage?
   A) iostat B) iotop ✅ C) top D) vmstat
   **Ans:** B

6. Which is true about RAID 0?
   A) Provides redundancy B) Stripes data across disks for performance ✅ C) Slower than single disk D) Double parity
   **Ans:** B

7. Rebuilding a large RAID array impacts performance mainly because:
   A) Network latency B) Increased I/O load on remaining disks ✅ C) CPU overload D) Memory leak
   **Ans:** B

8. Multipathing is used to:
   A) Increase MTU B) Provide multiple physical paths to storage for redundancy/perf ✅ C) Encrypt disks D) Compress traffic
   **Ans:** B

9. Which command shows block devices and mountpoints?
   A) lsblk ✅ B) fdisk -l C) blkid D) mount --show
   **Ans:** A

10. NVMe provides benefits over SATA because:
    A) Uses USB bus B) Uses PCIe and offers lower latency & higher parallelism ✅ C) Lower power consumption only D) It is slower
    **Ans:** B

11. Which is a sign of possible pending disk failure?
    A) Constant low temperature B) Increasing Reallocated\_Sector\_Ct ✅ C) Stable SMART values D) Noisy fans
    **Ans:** B

12. If disk IO %util is \~100% and latency is high, SRE should:
    A) Ignore it B) Identify hot VMs/processes and reduce IO or migrate them ✅ C) Reboot host immediately D) Delete all files
    **Ans:** B

---

## 12 — Short memorization checklist (pre-test drill)

* HDD vs SSD vs NVMe — latency/IOPS differences.
* RAID basics: 0/1/5/6/10 — tradeoffs (space vs performance vs redundancy).
* Key commands: `lsblk`, `smartctl`, `iostat`, `iotop`, `mdadm`, `multipath -ll`, `ipmitool sdr`.
* SMART attributes to watch: Reallocated\_Sector\_Ct, Current\_Pending\_Sector, Offline\_Uncorrectable.
* `iostat` columns: `await`, `%util` indicate device latency/ saturation.
* Rebuild impact: expect heavy I/O; plan maintenance windows.
* Multipath / path failover basics.
* Difference block/file/object and why Nutanix uses local disks aggregated by CVM.

---

## 13 — What to say in interview (frames)

* If asked about a slow VM: start with metrics — “I’d check VM and node IOPS/latency in Prism (or using iostat), check for rebuilds or background jobs, check SMART and controller logs, then decide to migrate VM or throttle background jobs.”
* If asked about a failed disk: “Confirm failure in logs and SMART, identify correct physical disk (serial/slot), schedule hot-swap per vendor guide, monitor rebuild and inform customers of temporary performance impact.”

---
# DevOps Tools & Practices — SRE-focused

## 1) Big picture — why this matters for SRE

SRE = keep systems healthy and scalable. DevOps tooling + monitoring = *automation, observability, and fast recovery*. You don’t need to be a deep software engineer — but you must know how to read/build simple automation, how pipelines/deployments work, and how to instrument & runbooks for incidents.

---

## 2) Version control (Git) — essentials you must know

Why: every config, script, infra-as-code lives in Git.

Must-know commands (practice these):

```bash
git clone <repo>
git status
git add file
git commit -m "short message"
git branch             # list branches
git checkout -b feature/xyz
git checkout main
git pull
git push origin feature/xyz
git merge main
git rebase -i HEAD~3   # interactive rebase (advanced)
git log --oneline --graph --decorate
git show <commit>
```

Interview traps: difference between `merge` and `rebase`; what is fast-forward merge; resolving conflicts. Be ready to explain branching model briefly (feature branches, PR review).

Hands-on lab:

1. Create repo, add file, commit, push to GitHub (or local bare repo).
2. Create branch, make change, merge via `git merge`.

Sample question: “How do you revert a bad commit that’s already pushed?” — answer: `git revert <commit>` (creates a safe revert commit).

---

## 3) CI/CD basics (concepts + common tools)

Concepts:

* **CI**: run tests/builds on commits (automated safety gate).
* **CD**: continuously deliver or deploy artifacts to environments.
* **Pipeline**: build → test → deploy steps.

Common tools: Jenkins, GitLab CI, GitHub Actions, CircleCI. You don’t need deep plugin knowledge — know the flow and a YAML pipeline example.

Simple GitHub Actions example:

```yaml
name: CI
on: [push, pull_request]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Run tests
        run: make test
```

SRE tasks: add health checks, automated rollbacks, canary/blue-green deployments to reduce blast radius.

Interview Q: “What is a canary deployment and why use it?” — short: deploy to small subset, monitor SLOs, roll back if errors spike.

---

## 4) Configuration management & IaC (Ansible / Terraform basics)

Why: replicate environments reliably.

Ansible (agentless, YAML playbooks):

* Run locally controlling remote machines via SSH.
* Mini-playbook:

```yaml
- hosts: web
  become: yes
  tasks:
    - name: install nginx
      apt:
        name: nginx
        state: present
```

Commands: `ansible-playbook -i inventory playbook.yml`

Terraform (declarative infra, stateful):

* Define resources in HCL, `terraform init`, `terraform plan`, `terraform apply`.
* Concepts: providers, state, modules, `terraform import`.

SRE tip: never store plain secrets in Git; use vault/secrets manager.

Interview Q: “How do you ensure idempotence in config management?” — answer: write tasks that can run multiple times without changing state; use modules with `state` flags.

---

## 5) Containers & orchestration (Docker + Kubernetes basics)

Docker essential commands:

```bash
docker build -t myapp:1.0 .
docker run -d --name web -p 8080:80 myapp:1.0
docker ps
docker logs -f web
docker exec -it web /bin/bash
docker images
docker rm -f <container>
docker rmi <image>
```

Kubernetes basics (must-know concepts):

* Pod, Deployment, Service, ConfigMap, Secret, Namespace
* `kubectl get pods`, `kubectl describe pod <name>`, `kubectl logs -f pod`, `kubectl apply -f deployment.yaml`
* Health probes: readiness vs liveness
* Rolling updates: `kubectl set image deployment/myapp myapp=image:tag` (or `kubectl rollout restart`)

SRE focus: understand how to find pod logs, attach to container, restart, scale, and how readiness/liveness affect traffic.

Interview Q: “What’s the difference between liveness and readiness probes?” — liveness restarts container when unhealthy; readiness controls if pod receives traffic.

---

## 6) Monitoring, metrics & alerting (Prometheus + Grafana, principles)

Observability pillars: **metrics, logs, traces**.

Prometheus (metrics):

* Pull model: Prometheus scrapes endpoints exposing `/metrics`.
* Metric types: counter, gauge, histogram, summary.
* Basic PromQL: `rate(http_requests_total[5m])`, `avg(node_cpu_seconds_total{mode="idle"}) by (instance)`.
* Alerts: configure alerting rules; Alertmanager handles routing (Slack, PagerDuty, email) and silencing.

Grafana:

* Visualize metrics + create dashboards, panels, blackbox exporter results.

SRE practices:

* Define SLIs and SLOs first, then set alerts based on symptom metrics, not just raw resource thresholds.
* Use burn-rate of error budget to trigger action: paging vs notify.

Example alert rule (Prometheus style):

```
- alert: HighErrorRate
  expr: rate(http_requests_total{job="api",status=~"5.."}[5m]) > 0.01
  for: 5m
  labels:
    severity: page
  annotations:
    summary: "High 5xx rate for API"
```

Interview Q: “What metric would you alert on for application latency?” — SLO: p99 latency > target; alert when p95/p99 breach sustained or error budget burn high.

---

## 7) Logging & tracing (ELK / EFK / Jaeger basics)

Logs:

* Components: Filebeat/Fluentd -> Logstash/Fluentd -> Elasticsearch -> Kibana (or Grafana Loki)
* Centralize logs for search, set retention, ensure structured logs (JSON) for easier parsing.

Tracing:

* Distributed tracing (Jaeger, Zipkin) to follow a request across services (helps find latency bottlenecks).
* Concepts: spans, traces, sampling.

SRE tip: correlate logs + traces + metrics during incidents (one view to root cause faster).

Interview Q: “If an API call is slow, what logs/metrics/traces do you look at?” — check metrics (latency, CPU, memory), check traces (where most time spent), check logs for errors/timeouts, correlate with infra metrics.

---

## 8) Alerting strategy & runbooks

* Triage flow: Alert → Acknowledge → Triage (is it P0/P1?) → Mitigate (hotfix/rollback) → Postmortem.
* Alerts should be actionable (who, what, how to mitigate). Avoid noisy alerts.
* Runbooks: short actionable steps for common incidents (service restart, failover, mitigation).

Example runbook outline:

1. Symptoms: what you see.
2. Impact: who/what is affected.
3. Steps: immediate checks & commands.
4. Rollback: how to revert.
5. Escalation contacts.

Interview Q: “How would you reduce alert fatigue?” — reduce false positives, use aggregated alerts, increase thresholds, implement SLO-based alerting.

---

## 9) Scripting for SRE (Bash + Python quick patterns)

Bash: quick operational scripts — know loops, conditionals, text parsing.
Example: check service and restart if down (basic — for practice only)

```bash
#!/usr/bin/env bash
svc="nginx"
if ! systemctl is-active --quiet "$svc"; then
  echo "$svc is down — restarting"
  sudo systemctl restart "$svc"
fi
```

Python: parse logs or simple report generator.
Example: count HTTP 500s in a logfile

```python
from collections import Counter
count = Counter()
with open('/var/log/nginx/access.log') as f:
    for line in f:
        parts = line.split()
        if len(parts) > 8:
            status = parts[8]
            if status.startswith('5'):
                count[status] += 1
print(count)
```

Interview Q: “Write a one-liner to list top 5 processes by memory?” — `ps aux --sort=-%mem | head -n 6`

---

## 10) Hands-on labs (do these; practical & fast)

1. **Git + CI**

   * Create tiny repo, add GitHub Actions YAML to run `echo "hello"` on push. Push and check run.

2. **Ansible**

   * Make simple inventory with localhost, run a playbook to create `/tmp/sre_test` directory.

3. **Docker**

   * `docker run -d --name nginx -p 8080:80 nginx`
   * `curl -I localhost:8080` — see 200 OK

4. **Prometheus + Node exporter (local)**

   * Run `prom/node-exporter` container, point Prometheus config to it, query `node_cpu_seconds_total`. (If full setup not possible, at least read basic metrics.)

5. **Logging**

   * Use `logger "test message"` and then `journalctl -n 50` to see messages. If running ELK locally is heavy — skip, but know the flow.

6. **Small Python script**

   * Save log parser above, run and verify counts.

---

## 11) Sample interview questions + model answers

1. **Q:** What’s an SLI vs SLO?
   **A:** SLI = measurable metric (e.g., request latency). SLO = target for that metric (e.g., 99.9% requests < 500ms).

2. **Q:** How do you debug a deployment that increased errors?
   **A:** Rollback to previous version (if risky), inspect build logs, check metrics (error rate, latency), review application logs & traces, check infra metrics (CPU, memory, disk), run smoke tests.

3. **Q:** What’s the difference between Prometheus and a logging system?
   **A:** Prometheus stores numeric time-series metrics (aggregations, alerts); logging systems store textual logs for search and deeper debugging. Both complement each other.

4. **Q:** How to ensure secrets don’t leak in CI logs?
   **A:** Use encrypted secrets in CI provider (GitHub secrets), mask secrets in logs, and avoid printing them in build steps.

---

## 12) 12-question MCQ drill (answers + quick why)

1. Which is a pull-based metrics system?
   A) Prometheus ✅ — Prometheus scrapes endpoints.
2. Which is used for log aggregation?
   A) Elasticsearch / Kibana ✅
3. Readiness probe controls:
   A) If pod receives traffic ✅
4. Which is for tracing distributed requests?
   A) Jaeger / Zipkin ✅
5. Alerting should primarily be based on:
   A) SLO violations / symptom metrics ✅
6. Which tool is agentless and uses SSH?
   A) Ansible ✅
7. `docker exec -it <container> /bin/bash` does:
   A) Start interactive shell inside container ✅
8. CI best practice to prevent faulty deploys:
   A) Run tests in CI before merge ✅
9. Which Prometheus expression gives per-second rate?
   A) rate(counter\_name\[5m]) ✅
10. To avoid noisy alerts, you should:
    A) Increase SLO-based thresholds and silence known flapping alerts ✅
11. In Kubernetes, to see live pod logs:
    A) kubectl logs -f podname ✅
12. To store Terraform state securely:
    A) Use remote backend like S3 with encryption and locking ✅

---

## 13) Quick memorization sheet (pre-test)

* Git basics + `git revert` vs `git reset` (revert is safe for pushed commits).
* CI flow: build → test → deploy; canary/blue-green basics.
* Ansible: YAML playbooks, idempotent tasks. Terraform: plan → apply.
* Docker commands + `kubectl get pods`, `describe`, `logs`, readiness vs liveness.
* Prometheus scrap model, `rate()`, alertmanager.
* Logging stack flow: beats/forwarder → parser → ES → Kibana.
* Runbook structure & SRE triage flow.

---
# SRE Principles — the mental model (short & practical)

* **Goal:** keep services healthy for customers (availability, latency, correctness) while enabling fast change.
* **Key terms:**

  * **SLI** — metric (e.g., request\_latency\_ms\_p99).
  * **SLO** — target for SLI (e.g., p99 < 500ms 99.9% of time).
  * **SLA** — contractual promise (may include penalties).
  * **Error budget** — allowed failure % (100% - SLO). Use it to decide if you can deploy changes.
* **Principles to use daily:** automate repetitive work (reduce toil), measure everything (observability), prefer small changes (reduce blast radius), blameless postmortems.

# Incident response — step-by-step playbook (memorize this order)

1. **Detect** — alert fires or customer report.
2. **Triage (5 min)** — determine scope & severity: Is production down? How many customers affected? Map to P0/P1/P2.
3. **Mitigate (first 15–30 min)** — reduce customer impact quickly (rollback, failover, scale up, kill noisy job). Use runbook actions.
4. **Collect evidence** — take logs, metrics, traces, screenshots, timestamps, command outputs (don’t lose ephemeral state).
5. **Communicate** — notify stakeholders & customers with status (who, what, where, ETA). Update regularly.
6. **Fix** — implement root fix or safe workaround.
7. **Recover & monitor** — verify service normal, monitor closely.
8. **Post-incident** — run blameless postmortem within 48–72 hours: timeline, root cause, action items, owners, deadlines. Track to closure.

# Triage decision tree (quick)

* Is the service completely down (no traffic)? → P0: page on-call, immediate mitigation.
* Partial degradation (some endpoints fail, increased errors)? → P1: page if customer-impacting SLAs broken or error budget burning fast.
* Minor degradation / single-user issue? → P2/P3: ticket & investigate.

# Concrete Commands to collect evidence (one-liners)

* Host health: `uptime; top -b -n1 | head -n 20; free -m; df -h`
* Disk I/O: `iostat -x 1 3; iotop -ao -b -n 5`
* Network: `ip a; ip route; ss -tuln; ss -s; ping -c4 <ip>; traceroute`
* Logs: `journalctl -u <svc> -n 200 --no-pager`; app logs: `tail -n 200 /var/log/<app>.log`
* Kubernetes: `kubectl get pods -A; kubectl describe pod <pod>; kubectl logs -f <pod>`
* Nutanix cluster: check Prism first (health, alerts), then CVM logs and `acli`/`ncli` outputs (if accessible).

# Fast mitigation recipes (memorize these actions)

* High CPU on a node: identify process (`top`), throttle/renice or migrate VM/customer load.
* High disk latency: identify noisy VM (`iostat`/Prism), throttle backups/snapshots, migrate VM to another node.
* Network outage (DNS): confirm IP reachability → `dig` → check `/etc/resolv.conf` and local caching; switch DNS server if needed.
* Service crash due to bad deploy: rollback to last good version (use CI/CD rollback), scale down new release, monitor error budget.

# Runbook template (copy & memorize; keep short)

1. Title & service name
2. Symptoms (what the alert shows)
3. Impact (customers, regions)
4. First check commands (exact commands to run)

   * e.g., `systemctl status svc; journalctl -u svc -n 200`
5. Quick mitigation steps (ordered, reversible)
6. Verification commands (how to confirm fixed)
7. Escalation contacts & runbook owner
8. Post-incident steps (logs to gather, next-day checks)

# Example runbook snippet (service not starting)

* Symptoms: `svc` in CrashLoop or not listening on port 8080.
* First checks:

  ```
  systemctl status svc
  journalctl -u svc -n 200 --no-pager
  ss -lntp | grep 8080
  ls -l /etc/<svc>/conf
  df -h /var
  ```
* Quick mitigations: revert last config change; check disk space; restart dependent services (DB); `systemctl restart svc`.
* Verify: `ss -lntp | grep 8080` and `curl -I http://localhost:8080/health`.

# Postmortem & RCA (what to produce)

* Timeline of events (with timestamps).
* Root cause (single sentence).
* Contributing factors (systemic).
* Corrective actions (permanent fix) + owners + target date.
* Preventive actions (alerts, automation, docs).
* Jira/trackable tasks; follow-up meeting.

RCA techniques: **5 Whys** (keep asking why until root cause), **Fishbone diagram** (categories: People, Process, Tools, Code, Infra), **Change analysis** (what changed recently).

# SLOs & alerting — practical math & examples

* Example SLI: request\_success\_rate = 1 - error\_rate.
* Example SLO: 99.9% success over 30 days. That allows 0.1% errors in that window. If 0.1% = 1 in 1000 requests. For 1M requests/day → allowed errors/day = 1000. Use error budget to decide whether to page.

Alerting tiers example:

* **Page (P0/P1)**: SLO burn-rate > 14x over last 1 hour OR p99 latency > threshold for 5 min → page on-call.
* **Notify (P2)**: p95 latency > threshold for 15 min → create ticket.
* **Info (P3)**: low-severity anomalies, aggregated reports.

Prometheus example (alert for p99 latency breach):

```yaml
- alert: HighP99Latency
  expr: histogram_quantile(0.99, sum(rate(http_request_duration_seconds_bucket[5m])) by (le)) > 0.5
  for: 5m
  labels: { severity: page }
  annotations: { summary: "p99 latency > 500ms" }
```

# Toil reduction & automation examples

* Identify repetitive tasks (e.g., reboots, log rotations) and automate via Ansible or small scripts.
* Example: automated health-check script that collects diagnostics and posts to an incident ticket (shell + curl to ticket API).
* Example snippet (bash) to gather diagnostics on alert and upload:

```bash
#!/usr/bin/env bash
svc=$1
out=/tmp/diag_${svc}_$(date +%s).tgz
mkdir -p /tmp/diag
journalctl -u $svc -n 500 > /tmp/diag/journal.txt
ss -tuln > /tmp/diag/sockets.txt
iostat -x 1 3 > /tmp/diag/iostat.txt
tar -czf $out -C /tmp diag
# optionally upload to storage and return URL
```

Automate this into alerting so first responder has immediate bundle.

# On-call best practices (short & interview-ready)

* Keep phone numbers & escalation in runbook.
* Use concise, repeated status updates (What we know, what we did, ETA).
* If paging is frequent, fix noisy alerting rules & raise SLO/threshold review.
* Rotate fairly and document handover notes. Always summarize before handing off.

# Interview-style scenario drills (practice answers aloud)

1. “Service error rate spiked to 5% in last 10 minutes — what do you do?”

   * Triage severity (is SLO breached?), mitigate (rollback or scale), collect metrics & logs, inform stakeholders, start postmortem.

2. “Customers in one datacenter report slowness — others ok.”

   * Isolate: check load balancer health & routing, check network path (traceroute), check local node health, check for zone-specific background jobs.

3. “A storage node lost a disk and cluster degraded; what steps?”

   * Confirm degraded state via Prism, identify failed disk (SMART & controller), follow platform safe replacement procedure, monitor rebuild, consider migrating critical VMs to healthy nodes if rebuild risk is high.

# Post-incident deliverable example (short)

* Title: “P99 latency spike — 2025-08-28”
* Impact: 12% of requests exceeded p99 threshold for 10 min (affecting 3 customers).
* Root cause: noisy backup job caused I/O saturation on Node X, causing remote reads to queue.
* Fix: throttle backup job + reschedule; moved 5 noisy VMs off Node X during peak.
* Preventive: add alert on IO await > 50ms and create automation to pause backups when IO high. Owner: Ananya (2 weeks).

# Quick checklist to memorize (for test + interview)

* Incident steps: Detect → Triage → Mitigate → Collect → Communicate → Fix → Recover → Postmortem.
* Runbook essentials: exact commands + mitigation steps + escalation contacts.
* Alerting principle: alert on symptoms, not symptoms-of-symptoms (e.g., page on error-rate, not CPU flapping unless it breaks SLO).
* RCA basics: 5 Whys, blameless postmortems, action items with owners.
* Automation: always favor safe, reversible fixes and idempotent scripts.

# Short quiz (8 Qs — answer & explanation after your replies)

I can give you these live now if you want — answer them and I’ll score/explain.
Examples I’ll ask:

1. What’s an error budget and how do you use it?
2. First three commands you run for service crash?
3. How do you decide to rollback vs mitigate?
4. What’s the difference between a Page and a Ticket alert?
5. How to avoid noisy alerts?
6. One-liner to collect last 200 lines of service journal?
7. Name two RCA techniques.
8. How to automate collection of diagnostics on alert?

---
# Topic — Scripting & Automation (Full, SRE-focused)

Goal: write small, safe, idempotent scripts you can use in interviews and on-call. We'll cover **Bash** (primary), quick **Python** scripts (for log parsing / small automation), debugging, safe practices, and 6 practice tasks + MCQs.

---

## A — Bash essentials (must-know for SRE)

### Basics

* Shebang: `#!/usr/bin/env bash`
* Useful flags: `set -euo pipefail`

  * `-e` exit on error, `-u` error on unset var, `-o pipefail` fail if any pipe part fails.
* Parameter expansion: `${VAR:-default}`, `${VAR:?error if unset}`.
* Test/condition: `if [ -f /etc/passwd ]; then ... fi` (use `[[ ... ]]` for safer pattern handling).

### Loops & control

```bash
for f in /var/log/*.log; do
  echo "$f"
done

while read -r line; do
  echo "$line"
done < file
```

### Functions & exit codes

```bash
log() { echo "[$(date -Is)] $*"; }
die() { log "ERROR: $*"; exit 1; }
```

### Safe command patterns

* Check before destructive ops: `[[ -d "$DIR" ]] || die "missing dir"`
* Use `mktemp` for temp files/dirs
* Use `trap 'cleanup' EXIT` to ensure cleanup

### Common one-liners you'll use / memorize

* Top 5 memory processes: `ps aux --sort=-%mem | head -n 6`
* Wait for a service to be up (with timeout):

```bash
timeout=30; while ! ss -lnt | grep -q ":8080"; do sleep 1; ((timeout--)); ((timeout==0)) && break; done
```

* Archive logs:

```bash
tar -czf /tmp/logs_$(date +%F_%H%M).tgz /var/log/myapp/*.log
```

---

## B — Idiomatic SRE bash script — Diagnostic bundle

Create `/usr/local/bin/gather_diag.sh` (explain each part)

```bash
#!/usr/bin/env bash
set -euo pipefail
svc=${1:-myservice}
OUT_DIR=$(mktemp -d /tmp/diag_${svc}_XXXX)
log(){ printf '%s %s\n' "$(date -Is)" "$*"; }
log "Collecting diag for $svc into $OUT_DIR"

journalctl -u "$svc" -n 500 > "$OUT_DIR/journal.log" || true
ss -tuln > "$OUT_DIR/sockets.txt" || true
iostat -x 1 3 > "$OUT_DIR/iostat.txt" || true
top -b -n1 > "$OUT_DIR/top.txt" || true
tar -czf "/tmp/${svc}_diag_$(date +%s).tgz" -C "$OUT_DIR" . && log "Bundle created"
rm -rf "$OUT_DIR"
```

Explain: safe — non-fatal commands (`|| true`) to avoid exiting; uses `mktemp`; writes bundle to `/tmp`.

---

## C — Bash practice tasks (do these now)

1. Write a one-liner to list all files >100MB in `/var`:

```bash
find /var -xdev -type f -size +100M -exec ls -lh {} \; | awk '{print $5, $9}'
```

2. Rotate logs older than 7 days:

```bash
find /var/log/myapp -type f -mtime +7 -exec gzip {} \;
```

3. Safe restart-if-down script (idempotent):

```bash
svc="nginx"
if ! systemctl is-active --quiet $svc; then
  systemctl restart $svc
fi
```

---

## D — Python for SRE — quick & practical

Why: easier parsing, JSON, HTTP, and more structure for small automation.

### Must-know:

* Files & text parsing
* subprocess usage (`subprocess.run`)
* Requests/HTTP (use `requests` in non-restricted env)
* Virtualenv basics (optional)

### Small useful script — parse nginx access log for 5xx

```python
#!/usr/bin/env python3
from collections import Counter
from pathlib import Path
log = Path('/var/log/nginx/access.log')
cnt = Counter()
with log.open() as fh:
    for line in fh:
        parts = line.split()
        if len(parts) > 8:
            status = parts[8]
            if status.startswith('5'):
                cnt[status] += 1
for code, c in cnt.most_common():
    print(code, c)
```

### Run shell commands from Python (safe)

```python
import subprocess
res = subprocess.run(['ss','-tuln'], capture_output=True, text=True, check=False)
print(res.stdout)
```

---

## E — Scripting best practices (interview points)

* Make scripts **idempotent** and **safe by default**.
* Never hardcode secrets; read from env or vault.
* Log timestamps and actions.
* Provide `--dry-run` flag when scripts modify state.
* Unit test longer scripts where possible (small functions).

---

## F — Automation examples (quick recipes)

1. Auto-rotate & alert on disk full: cron job runs script that `df -h`, if usage > 90% sends alert (curl to webhook).
2. Auto-collect diag on alert: integrate `gather_diag.sh` into alert action (Prometheus Alertmanager webhook).
3. Auto-heal simple issues: if service down and restart succeeds twice, open ticket and notify.

---

## G — Practice exercises (try and submit answers here)

1. Create a bash script that: checks `/etc/resolv.conf` has a nameserver; if not, appends `nameserver 8.8.8.8` to a temp file and prints “fixed” (safe write not directly edit).
2. Python: write a script that reads `/var/log/syslog` and prints top 3 most frequent error messages (use word frequency).

I can grade your solutions if you paste them.

---

## H — Common interview scripting questions & model answers

1. **Q:** Write a one-liner to show top 10 processes by disk I/O.
   **A:** `sudo iotop -ao -b -n 5 | head -n 20` (explain iotop output and limitations)

2. **Q:** How would you safely test a script that restarts services?
   **A:** Use `--dry-run`, run on staging, run with a non-destructive command (echo), add `set -euo pipefail`, and have explicit checks & logs.

3. **Q:** How to parse JSON output from an API in bash?
   **A:** Use `jq` (example: `curl -s http://api | jq '.items[] | {id: .id, status: .status}'`).

---

## I — Mini scripting MCQ (6 questions)

1. `set -euo pipefail` does NOT do:
   A) Treat unset variables as errors
   B) Exit on command failure
   C) Automatically log all commands to syslog ✅
   D) Cause pipeline failure propagation
   **Ans:** C

2. Which Python lib is best for HTTP requests in scripts?
   A) urllib B) requests ✅ C) httplib D) urllib2
   **Ans:** B

3. To avoid partial writes when creating file `/etc/my.conf`, use:
   A) `> /etc/my.conf` B) `echo "x" > /tmp/tmpfile && mv /tmp/tmpfile /etc/my.conf` ✅ C) `cp` D) `sudo tee`
   **Ans:** B (atomic move)

4. Meaning of exit code `0` from a script:
   A) Success ✅ B) Error C) Unknown D) Waiting
   **Ans:** A

5. A safe way to run a destructive script only interactively:
   A) Require `--confirm` flag and `tty` check ✅ B) Always run automatically C) Run as root without warning D) Use `cron`
   **Ans:** A

6. Which is better for long-running automation in infra?
   A) Cron B) Systemd timers ✅ C) At D) Manual
   **Ans:** B (systemd timers provide management & logging)

---


# Topic — **Soft Skills & Mock Interviews** (complete package for Nutanix SRE intern)

# 1 — Interview mindset & top principles

* **Be calm, clear, concise.** Structure answers (Problem → Action → Result).
* **Customer-first framing.** Always tie tech fixes to customer impact.
* **Honesty + curiosity.** If you don’t know, say “I don’t know exactly, here’s how I’d find out.” That’s respected.
* **Show troubleshooting thought process out loud.** Interviewers care *how* you think more than memorized facts.
* **Match Nutanix values:** Hungry, Humble, Honest, with Heart. Be eager to learn, admit limits, show empathy for customers.

---

# 2 — Elevator pitch (30–45s) — memorize and adapt

“Hi, I’m **Aditya** — a final-year CS student with hands-on Linux and networking experience. I enjoy debugging and building small automation to reduce toil. Recently I diagnosed and fixed a VM performance issue by analyzing `iostat`/`top`, identifying a noisy backup job, and automating alerts that prevented recurrence. I’m excited about Nutanix because I want to work at the intersection of virtualization, storage and customer-facing reliability.”

(Shorten / personalize — practice out loud.)

---

# 3 — The STAR method — quick template

* **Situation** — set context (1 sentence).
* **Task** — what you had to achieve.
* **Action** — steps you took (focus here).
* **Result** — measurable outcome & learning.

Always end with what you learned or what you’d do differently.

---

# 4 — 8 strong STAR story ideas you can adapt

(Write 2–3 sentences for Situation/Task and 4–6 lines for Action/Result for each.)

1. **Incident debug (VM slow)**

   * S: University VM lab was slow. T: Restore performance for affected students. A: Collected `top/iostat`, found backup I/O spike during lab hours, rescheduled backups and implemented alert; automated kill script for runaway process. R: Reduced complaints by 90%; latency returned to normal.

2. **Service restart automation**

   * S: Repeated manual restarts for an app during tests. T: Remove toil. A: Wrote idempotent Bash script with `systemctl` checks and `--dry-run`, added to cron with alert on failure. R: Saved \~2 hours/week and reduced mean time to restore.

3. **Network outage triage**

   * S: Team lost internet for a lab cluster. T: Restore connectivity. A: Ran `ip a`, `ip route`, pinged gateway, found DHCP lease expiry and stale switch port; cleared port and renewed leases. R: Restored services in 20 minutes.

4. **Team project leadership**

   * S: Final year project with 4 members; JD required deadline. T: Deliver prototype. A: Organized sprints, used Git branching, CI to auto-run tests, resolved merge conflicts. R: Delivered demo on time; project selected for showcase.

5. **Customer-oriented troubleshooting**

   * S: Real user could not SSH after change. T: Convince customer and fix. A: Explained steps simply, ran live commands (`journalctl`), fixed `sshd_config`, validated, documented fix in KB. R: Customer satisfied; knowledge base entry reused.

6. **Learning on the job**

   * S: Needed to understand a new monitoring tool. T: Get productive in 48 hours. A: Read docs, created dashboard, wrote alert rules and runbook. R: Decreased alert noise by tuning thresholds.

7. **Conflict resolution**

   * S: Two teammates disagreed on solution design. T: Reach consensus. A: Facilitated pros/cons discussion, prototyped both, measured performance. R: Chosen approach had measurable performance improvement.

8. **Failure recovery**

   * S: Disk warning in lab server. T: Replace safely. A: Verified via SMART, scheduled replacement during low usage, followed vendor checklist, monitored rebuild. R: No data loss and minimal downtime.

(Write these up for your answers; have 3–4 polished stories ready.)

---

# 5 — Common HR / Behavioral Qs + model answers (tailored to Nutanix SRE)

1. **Tell me about yourself.**
   Short pitch + 1 relevant achievement + why Nutanix. (Use elevator pitch.)

2. **Why SRE and not development?**
   “I like building reliability and helping customers quickly. SRE mixes debugging, system design and automation — I enjoy incident triage and reducing toil.”

3. **Why Nutanix?**
   “Nutanix leads HCI and simplifies cloud operations; I want to learn HCI internals (AHV, CVM) while solving customer-facing reliability problems.”

4. **Tell me about a time you failed.**
   S/T/A/R: show what went wrong, what you fixed, and lessons learned. Emphasize growth and humility.

5. **How do you handle customer pressure?**
   “Prioritize safety: clarify impact, triage, communicate ETA, apply mitigation, document. Calm, clear status updates reduce customer anxiety.”

6. **Describe a time you worked cross-functionally.**
   Example: worked with devs to reproduce bug, then coordinated with infra to patch and release.

7. **How do you learn new tech quickly?**
   Explain a 48–72 hour approach: docs → hands-on lab → test scenarios → quick runbook.

8. **What are your strengths & weaknesses?**
   Strength: methodical troubleshooting & automation; Weakness: initially over-detailed documentation — improving by writing concise runbooks.

---

# 6 — Technical interview script (mock rounds) — use this to practice aloud

I give you **Round 1 (MCQ quiz)** → **Round 2 (technical interview)** → **Round 3 (deep technical / scenario)** → **HR**. Practice answering out loud or record yourself.

## Round 1 — Sample 20-question MCQ (timed: 60 min in real test; practice: 20–30 min)

(You should be able to answer quickly; I’ll include 20 sample Qs — you can practice in timed mode later.)

1. `df -h` shows? (A disk usage)
2. `ps aux` column `%CPU` shows? (CPU percent)
3. `journalctl -u nginx` shows? (unit logs)
4. Which port: SSH? (22)
5. Which tool capture packets? (tcpdump)
6. /etc/resolv.conf contains? (nameserver entries)
7. `iostat` column `await` indicates? (avg I/O latency)
8. /proc/cpuinfo shows? (CPU info)
9. Subnet /26 = how many usable hosts? (62)
10. DNS query uses UDP 53 by default? (True)
11. Hypervisor type for AHV? (Type 1)
12. CVM role? (distributed storage services)
13. RAID 1 gives? (mirroring)
14. `ss -tuln` shows? (listening sockets)
15. SIGKILL cannot be trapped? (True)
16. Which command shows inode usage? (df -i)
17. What is swappiness? (kernel tuning for swap usage)
18. What is /etc/hosts used for? (local name resolution)
19. `ping 8.8.8.8` works but `dig google.com` fails — likely? (DNS)
20. Error budget definition? (allowed failures vs SLO)

(If you want, I’ll grade your answers when you attempt.)

## Round 2 — Live technical Q\&A (behavioral + practical) — 25–35 minutes

Practice answering these, then compare to model answers below.

1. Explain how you would debug: “I can’t SSH into my VM.”
2. A service won’t start after reboot. What commands do you run first?
3. Explain differences between HDD and SSD; when would you choose each?
4. What is a CVM in Nutanix and why is it important?
5. Can a guest VM have more memory than the host? Explain.
6. Explain TCP three-way handshake.
7. How does DNS resolution work? (brief)
8. Give steps to find which process is using most disk I/O.
9. Explain SLI, SLO, SLA difference in simple terms.
10. How do you approach a performance incident where p99 latency spikes?

### Model answers — short versions (practice paraphrasing)

1. SSH fail: check network (`ip a`, `ip route`), check port open on server (`ss -tuln | grep :22`), check `sshd` status (`systemctl status sshd`), check firewall (`iptables -L`), check server logs (`journalctl -u sshd`), validate keys and `/etc/hosts.allow`. Reproduce with `ssh -v`. Communicate to user.

2. Service not starting: `systemctl status svc` → `journalctl -u svc` → check config syntax → check ports (`ss -tuln`) → check mounts & disk space (`df -h`) → check permissions (`ls -l`), then restart.

3. HDD vs SSD: HDD cheaper large capacity, higher latency, lower IOPS; SSD lower latency/higher IOPS, more expensive. Choose HDD for cold storage/backup; SSD for databases/VMs needing random IOPS.

4. CVM: Controller VM on each Nutanix node that manages distributed storage and cluster services; important because it abstracts local disks into cluster storage and provides resiliency.

5. Guest more memory than host: No — the host physical memory limits total. Overcommit can assign more virtual memory than physical but leads to swapping/ballooning; cannot actually provide more real RAM than exists.

6. TCP handshake: SYN → SYN-ACK → ACK; establishes seq/ack numbers and connection state.

7. DNS: app → OS resolver → /etc/hosts → system resolver sends UDP query to configured nameserver → recursive resolver contacts authoritative servers → returns A/AAAA to client.

8. Disk I/O top processes: `iostat -x 1` to identify device latency; `iotop -ao` to find processes; `lsof /dev/sda` to map files.

9. SLI = metric; SLO = target for metric; SLA = contractual promise based on SLO.

10. p99 spike: triage scope → check service metrics & traces for hotspots → correlate infra (CPU/Disk/Network) → identify offending component/route → mitigate (scale, rollback, throttle) → monitor & postmortem.

## Round 3 — Deep technical / scenario (30–40 min)

Practice structure: Clarify → Hypothesize → Test (commands) → Mitigate → Prevent.

Sample scenarios to practice, verbally, for each: give exact commands you’d run and explain why.

1. Nutanix node shows degraded storage, one CVM offline.
2. Customers in one VLAN cannot access VMs only in that rack.
3. Live migration fails with MTU mismatch errors.
4. Large nightly backup causes cluster-wide latency during business hours.

(You should rehearse 3–4 minute answers for each.)

---

# 7 — HR Round script (questions + answers) — final stage

Typical HR questions and suggested answers:

1. **Walk me through your resume.**

   * Short education, one strong project, key skills (Linux, networking, virtualization), and why SRE at Nutanix fits.

2. **Tell me about a time you had to learn fast.**

   * Use STAR: describe quick self-training + outcome.

3. **Why should we hire you?**

   * Emphasize customer focus, willingness to learn, hands-on Linux & networking knowledge, eagerness to solve incidents.

4. **What are your salary expectations?**

   * For intern roles, discuss stipend/CTC range appropriate to campus norms (or say you’re flexible, eager to join).

5. **Any questions for us?** (Must ask—examples)

   * “What does a typical day for an SRE intern at Nutanix look like?”
   * “What level of mentorship/support will interns receive?”
   * “What’s the most important skill a successful intern at Nutanix has developed?”
   * “What are the next steps and timeline?”

---

# 8 — What to say when you don’t know a technical answer

1. Admit: “I don’t know the exact command/answer.”
2. Propose diagnostic steps: “Here’s how I’d find out — check X, Y, Z.”
3. If partial knowledge exists, explain it and say what you’d verify.
4. Offer to follow-up (if appropriate). Example: “I’m not sure of the exact code path in AHV offhand, but I’d check Prism alerts, CVM logs, and then escalate to engineering with logs.”

This is far better than bluffing.

---

# 9 — Body language, voice & communication tips (onsite & virtual)

**Onsite:**

* Dress neat-casual or business casual depending on campus culture. Clean shoes.
* Firm handshake (if cultural), eye contact, smile.
* Sit upright, lean slightly forward when engaged. Speak clearly.

**Virtual:**

* Use neutral background, good lighting (face lit), camera at eye level.
* Use headphones with mic; mute when not speaking.
* Keep answers slightly shorter (speak clearly). Use shared screen only when asked.

**Tone:**

* Calm, confident, not arrogant. Use “we” when describing team work. Be concise.

---

# 10 — Final day checklist (physical & mental)

**Day before**

* Revise cheat-sheets (OS commands, network subnetting, Nutanix facts).
* Do 30–60 mins hands-on labs (systemctl, journalctl, ip, ss, dig).
* 15-minute mock aloud: elevator pitch + 2 STAR stories.

**What to bring (onsite)**

* Several printed resumes (neat).
* Notepad + pen (write runbook points if needed).
* Student ID, offer letter (if follow-up), copies of certificates.
* Water bottle, light snack.
* Phone charged, power bank.

**At interview**

* Ask clarifying questions (don’t guess ambiguous asks).
* Speak structured answers.
* If given a problem, narrate steps and check assumptions.
* End with 2–3 questions you want to ask (see HR Qs above).

---

# 11 — 24-hour final crash plan (exact schedule)

* 0–1 hr: Quick hands-on (top, free, df, ss, dig, traceroute, journalctl).
* 1–2 hr: Revise Nutanix CVM/AHV/Prism flashcards + HCI definition.
* 2–3 hr: Subnetting drill (10 problems) + memorize common ports.
* 3–4 hr: Mock technical Q\&A (use the script above).
* 4–5 hr: STAR stories polish + elevator pitch.
* Night: sleep early. Morning: light review, hydrate, arrive 15–20 min early.

---

# Topic A — Operating Systems & Linux (15 MCQs)

1. `df -h` reports:
   A) Running processes
   B) Disk usage by filesystem (human) ✅
   C) Memory usage
   D) Network stats
   **Why:** `df` = disk free; `-h` = human readable.

2. Which signal can be trapped (handled) by a process?
   A) SIGKILL
   B) SIGTERM ✅
   C) SIGSTOP
   D) SIGKILL+SIGSTOP
   **Why:** SIGTERM can be caught; SIGKILL cannot.

3. `ps aux --sort=-%mem | head` shows:
   A) Top CPU processes
   B) Top memory processes ✅
   C) Kernel threads
   D) Disk I/O usage
   **Why:** Sorted by `%mem`.

4. Which file controls resolver nameservers on most Linux systems?
   A) /etc/hosts
   B) /etc/resolv.conf ✅
   C) /etc/network/interfaces
   D) /etc/dns.conf
   **Why:** `resolv.conf` lists nameserver IPs.

5. High `si/so` in `vmstat` indicates:
   A) High CPU usage
   B) High swap activity ✅
   C) High network traffic
   D) Low disk usage
   **Why:** `si` = swap in, `so` = swap out.

6. What does `inode` store?
   A) File content
   B) File metadata (permissions, pointers) ✅
   C) File name only
   D) Filesystem UUID
   **Why:** Inode holds metadata; directory maps name→inode.

7. Which command shows kernel ring buffer messages?
   A) journalctl
   B) dmesg ✅
   C) syslog
   D) tail
   **Why:** `dmesg` reads kernel log buffer.

8. To find largest directories under /var:
   A) du -sh /var/\* | sort -h ✅
   B) df -h /var
   C) ls -lh /var
   D) find /var -type d -size +1G
   **Why:** `du` summarizes disk usage per dir.

9. `systemctl status nginx` returns non-zero exit — next check:
   A) journalctl -u nginx ✅
   B) dmesg only
   C) apt update
   D) fdisk -l
   **Why:** `journalctl -u` shows service logs.

10. Which permission string indicates SUID is set?
    A) -rwxr-xr-x
    B) -rwsr-xr-x ✅
    C) -rwxr-sr-x
    D) drwxrwxrwt
    **Why:** `s` in user execute = SUID.

11. Which file shows process-specific info like open fds?
    A) /proc/meminfo
    B) /proc/<pid>/fd ✅
    C) /etc/passwd
    D) /var/log/syslog
    **Why:** `/proc/<pid>/fd` lists file descriptors.

12. Which `top` column shows percentage CPU?
    A) %MEM
    B) %CPU ✅
    C) VIRT
    D) RES
    **Why:** `%CPU` is CPU usage percent.

13. What is purpose of `swap`?
    A) Faster CPU scheduling
    B) Kernel module storage
    C) Extend RAM on disk for inactive pages ✅
    D) Store logs
    **Why:** Swap stores pages not currently in RAM.

14. Which command shows mounted filesystems?
    A) mount ✅
    B) df -i
    C) lsblk -f
    D) umount
    **Why:** `mount` lists mounts and options.

15. How to view last boot logs via systemd?
    A) journalctl -b ✅
    B) dmesg -b
    C) tail /var/log/dmesg
    D) last -x
    **Why:** `journalctl -b` shows journal since boot.

---

# Topic B — Computer Networking (15 MCQs)

1. OSI layer responsible for IP routing is:
   A) Data Link
   B) Network ✅
   C) Transport
   D) Application
   **Why:** Network layer handles IP & routing.

2. TCP vs UDP: which is connectionless?
   A) TCP
   B) UDP ✅
   C) Both
   D) Neither
   **Why:** UDP is connectionless and unreliable.

3. `192.168.1.50/26` network address is:
   A) 192.168.1.0
   B) 192.168.1.64 ✅
   C) 192.168.1.128
   D) 192.168.1.192
   **Why:** /26 block size 64; 50 falls in .0? Wait: block boundaries 0,64 — 50→ network 0. Oops — careful.
   **Correction:** Actually 192.168.1.50 is in 0–63 → network `192.168.1.0`.
   **Final Answer:** B was wrong — correct is A) 192.168.1.0.
   *(Lesson: always compute block boundaries: /26 → 64-address blocks starting at .0)*

4. `dig +short google.com` returns:
   A) HTTP headers
   B) Resolved IP addresses ✅
   C) DNS server list
   D) No output ever
   **Why:** `dig +short` prints A/AAAA records.

5. Default DNS query transport:
   A) TCP 53
   B) UDP 53 ✅
   C) ICMP
   D) HTTP
   **Why:** DNS uses UDP by default for queries.

6. `ip route` shows:
   A) open sockets
   B) routing table ✅
   C) arp cache
   D) dns entries
   **Why:** `ip route` prints routes.

7. Which tool traces path & hops?
   A) ping
   B) traceroute ✅
   C) dig
   D) ss
   **Why:** `traceroute` shows hop-by-hop path.

8. Which port is HTTPS?
   A) 80
   B) 21
   C) 443 ✅
   D) 22
   **Why:** HTTPS uses TCP 443.

9. APIPA addresses start with:
   A) 10.0.x.x
   B) 169.254.x.x ✅
   C) 172.16.x.x
   D) 192.168.x.x
   **Why:** Automatic Private IP (link-local) uses 169.254/16.

10. `ss -tuln` lists:
    A) routing entries
    B) listening TCP/UDP sockets ✅
    C) mounted filesystems
    D) process tree
    **Why:** `ss` lists sockets with flags.

11. Which is used to resolve MAC from IP on L2?
    A) DNS
    B) ARP ✅
    C) DHCP
    D) ICMP
    **Why:** ARP maps IPv4→MAC.

12. DHCP server uses which UDP ports?
    A) 67/68 ✅
    B) 53/53
    C) 80/443
    D) 20/21
    **Why:** DHCP server/clients use UDP 67/68.

13. What is purpose of MTU?
    A) Max transfer unit — largest packet size on link ✅
    B) Routing table size
    C) DNS TTL
    D) Port multiplexing
    **Why:** MTU defines max payload without fragmentation.

14. `tcpdump -n -i eth0 port 80 -c 20` will:
    A) Capture 20 packets on eth0 for port 80 without name resolution ✅
    B) Show 20 lines of netstat
    C) Traceroute 20 hops
    D) Restart network
    **Why:** `-c 20` stops after 20 packets; `-n` no DNS.

15. A `/30` IPv4 subnet has how many usable hosts?
    A) 0
    B) 1
    C) 2 ✅
    D) 4
    **Why:** /30 → 4 addresses, 2 usable (point-to-point links).

---

# Topic C — Virtualization & Cloud / Nutanix (15 MCQs)

1. Type-1 hypervisor runs:
   A) On top of host OS
   B) Directly on hardware ✅
   C) Only in containers
   D) In browser
   **Why:** Type-1 = bare-metal.

2. AHV is Nutanix’s native hypervisor based on:
   A) Xen
   B) KVM ✅
   C) Hyper-V
   D) VMware ESXi
   **Why:** AHV is KVM-based.

3. What is a Nutanix CVM?
   A) Client VM
   B) Controller VM that manages storage & services ✅
   C) Backup VM
   D) Container VM
   **Why:** CVM handles distributed storage/control.

4. Live migration moves a VM:
   A) With downtime always
   B) While running, ideally zero downtime ✅
   C) Only when powered off
   D) Only between clouds
   **Why:** Live migration transfers memory state while VM runs.

5. Scale-out means:
   A) Add resources to single node
   B) Add more nodes horizontally ✅
   C) Decrease cluster size
   D) Replace hardware
   **Why:** Scale-out = add nodes.

6. Snapshot is best described as:
   A) Full clone always
   B) Point-in-time copy (often copy-on-write) ✅
   C) Hardware replacement
   D) Load balancer config
   **Why:** Snapshots capture state at a point.

7. Containers share:
   A) Kernel with host ✅
   B) Full OS with separate kernel
   C) Hypervisor resources directly
   D) None of the above
   **Why:** Containers rely on host kernel namespaces.

8. Which cloud model provides VMs as a service?
   A) SaaS
   B) PaaS
   C) IaaS ✅
   D) DaaS
   **Why:** IaaS offers VM/instance provisioning.

9. Prism is:
   A) Nutanix UI for management & monitoring ✅
   B) Nutanix hypervisor
   C) Nutanix kernel module
   D) Storage device
   **Why:** Prism is the management plane.

10. Can a guest VM have more *physical* RAM than host?
    A) Yes, always
    B) No — physical RAM limited by host ✅
    C) Only with swap
    D) Only on AHV
    **Why:** Host physical RAM bounds actual physical memory.

11. Erasure coding in distributed storage mainly trades:
    A) CPU for memory
    B) Capacity efficiency vs compute overhead ✅
    C) Network for GPU
    D) Latency for TTL
    **Why:** Erasure coding reduces storage overhead at compute/latency cost.

12. Which is a Type-2 hypervisor example?
    A) ESXi
    B) KVM AHV
    C) VirtualBox ✅
    D) Hypervisor-X
    **Why:** VirtualBox runs on host OS -> Type-2.

13. One-click full-stack upgrade refers to updating:
    A) Only OS
    B) Firmware + hypervisor + Nutanix software ✅
    C) Only CVM
    D) Only application stack
    **Why:** Nutanix advertises full-stack upgrade.

14. Nutanix HCI primarily aggregates:
    A) Central SAN storage
    B) Local disks across nodes via software ✅
    C) Cloud buckets only
    D) Tape libraries
    **Why:** HCI pools local disks into distributed FS.

15. AHV provides which built-in feature?
    A) vMotion-like live migration ✅
    B) Only manual migration
    C) No HA support
    D) Only container runtime
    **Why:** AHV includes live migration and HA.

---

# Topic D — Hardware & Storage (15 MCQs)

1. SSD vs HDD: which has lower random I/O latency?
   A) HDD
   B) SSD ✅
   C) Both same
   D) Tape
   **Why:** SSDs have no moving parts and much lower latency.

2. Typical HDD random IOPS order of magnitude:
   A) 10⁵
   B) 10³
   C) 10² ✅
   D) 10⁶
   **Why:** HDDs \~50–200 IOPS; SSDs much higher.

3. RAID 1 provides:
   A) Striping only
   B) Mirroring (redundancy) ✅
   C) Parity across disks
   D) No redundancy
   **Why:** RAID1 mirrors data.

4. RAID 5 write penalty mainly due to:
   A) Parity calculation & extra reads/writes ✅
   B) Network latency
   C) CPU only
   D) No penalty
   **Why:** Parity update requires extra I/O ops.

5. SMART attribute to check for reallocated sectors:
   A) Power\_On\_Hours
   B) Reallocated\_Sector\_Ct ✅
   C) Model\_Family
   D) Serial\_Number
   **Why:** Reallocated sectors indicate bad sectors.

6. `iostat` column `await` is:
   A) Avg time for I/O request completion ✅
   B) CPU idle time
   C) Network latency
   D) Swap usage
   **Why:** `await` measures I/O latency.

7. Multipathing provides:
   A) Multiple paths to storage for redundancy/perf ✅
   B) More CPUs
   C) Faster CPU caches
   D) Extra memory
   **Why:** Multipathing gives path redundancy.

8. NVMe uses which bus?
   A) SATA
   B) SAS
   C) PCIe ✅
   D) USB
   **Why:** NVMe is a PCIe protocol for flash.

9. What is IOPS?
   A) Input/Output per second (I/O operations per second) ✅
   B) Internal OS process
   C) Internet ops per second
   D) Idle operations
   **Why:** IOPS measures I/O rates.

10. Which command shows block devices & mounts?
    A) lsblk ✅
    B) ps aux
    C) netstat
    D) top
    **Why:** `lsblk` lists block devices and mountpoints.

11. Rebuilding RAID after disk replacement often causes:
    A) Lower I/O load
    B) High I/O load on remaining disks ✅
    C) Network partition
    D) CPU burn only
    **Why:** Rebuild reads/writes impact remaining disks.

12. Enterprise SSDs often expose which metric important for lifespan?
    A) Bytes\_Written (TBW) ✅
    B) RPM
    C) Seek\_Time
    D) Cylinder\_Count
    **Why:** SSD endurance measured in TBW.

13. Which is best for high random IOPS workload?
    A) HDD
    B) SATA SSD
    C) NVMe SSD ✅
    D) Tape
    **Why:** NVMe offers highest IOPS & low latency.

14. What does `smartctl -a /dev/sda` show?
    A) Network interfaces
    B) SMART health & stats for disk ✅
    C) Kernel modules
    D) Memory map
    **Why:** `smartctl` queries drive SMART.

15. Object storage (S3) is typically accessed via:
    A) iSCSI block protocol
    B) NFS mount only
    C) HTTP API (REST) ✅
    D) SMB
    **Why:** Object stores use HTTP APIs like S3.

---

# Topic E — Troubleshooting & Debugging (15 MCQs)

1. First step when service is reported down:
   A) Reinstall OS
   B) Triage scope & severity ✅
   C) Panic
   D) Delete logs
   **Why:** Triage to understand impact before action.

2. To view service logs for systemd unit `app`:
   A) cat /var/log/app.log
   B) journalctl -u app ✅
   C) systemctl list
   D) ps aux | grep app
   **Why:** `journalctl -u` shows unit logs.

3. If `ping 8.8.8.8` works but `ping google.com` fails, likely cause:
   A) Disk full
   B) DNS resolution issue ✅
   C) CPU overload
   D) Inode exhaustion
   **Why:** IP reachable but name fails → DNS.

4. To find which process listens on port 3306:
   A) ss -tuln | grep 3306
   B) ss -tulpn | grep 3306 ✅
   C) df -h
   D) top
   **Why:** `-p` reveals PID/process.

5. OOM killer logs can be found in:
   A) /var/log/boot.log
   B) dmesg or journalctl (kernel messages) ✅
   C) /etc/passwd
   D) /proc/cpuinfo
   **Why:** Kernel logs record OOM events.

6. To check disk usage by directory:
   A) df -h
   B) du -sh /path ✅
   C) ps aux
   D) free -m
   **Why:** `du` sums directory sizes.

7. If `systemctl status svc` shows “Failed with result 'exit-code'”, next is:
   A) Reboot system
   B) journalctl -u svc -n 200 ✅
   C) Stop all services
   D) Remove package
   **Why:** Check service logs for failure cause.

8. High `%iowait` in `top` suggests:
   A) network congestion
   B) CPU waiting on disk I/O ✅
   C) memory leak
   D) process stuck in syscalls
   **Why:** `%iowait` indicates time CPU idle waiting for I/O.

9. For intermittent network packet loss, useful command is:
   A) traceroute or mtr ✅
   B) iostat
   C) smartctl
   D) lsblk
   **Why:** traceroute/mtr identify problematic hops.

10. Recommended first action on critical incident:
    A) Notify stakeholders & mitigate (page) ✅
    B) Wait an hour
    C) Change code blindly
    D) Delete logs
    **Why:** Communication + mitigation are urgent.

11. Which helps correlate distributed requests?
    A) Logs only
    B) Tracing (Jaeger) ✅
    C) iostat
    D) ls -l
    **Why:** Traces show end-to-end spans.

12. To quickly check if disk is failing:
    A) smartctl -a /dev/sdX ✅
    B) ping host
    C) ip a
    D) journalctl -u ssh
    **Why:** SMART reports disk health stats.

13. When debugging SSH failure, which is least useful?
    A) ssh -v client output
    B) journalctl -u sshd on server
    C) cat /etc/ssh/sshd\_config
    D) df -i (inodes) ✅ (least immediate)
    **Why:** Inodes can matter but not first check; others directly relevant.

14. Best practice for incident notes:
    A) Blame person
    B) Record timeline & actions (blameless) ✅
    C) Delete conversation
    D) Ignore follow-up
    **Why:** Blameless timeline aids RCA.

15. After mitigation, next step is:
    A) Close ticket immediately
    B) Run postmortem and preventive actions ✅
    C) Ignore incident
    D) Uninstall monitoring
    **Why:** Postmortem prevents recurrence.

---

# Topic F — Scripting, Automation & DevOps Tools (15 MCQs)

1. Which Git command safely reverts a pushed commit creating a new commit?
   A) git reset --hard
   B) git revert ✅
   C) git rm
   D) git stash
   **Why:** `git revert` creates a new commit reversing changes.

2. CI stands for:
   A) Continuous Integration ✅
   B) Code Inspection
   C) Continuous Improvement
   D) Container Instance
   **Why:** CI automates builds/tests on commits.

3. Ansible is:
   A) Agent-based config mgmt
   B) Agentless, SSH-based config mgmt ✅
   C) A language runtime
   D) A hypervisor
   **Why:** Ansible uses SSH to manage nodes.

4. Docker command to run container in background:
   A) docker run --rm image
   B) docker run -it image
   C) docker run -d image ✅
   D) docker stop image
   **Why:** `-d` runs detached.

5. Kubernetes object controlling pods and rollout:
   A) Pod
   B) Service
   C) Deployment ✅
   D) Secret
   **Why:** Deployment manages replica sets/rollouts.

6. Prometheus scrapes metrics via:
   A) Push model
   B) Pull model ✅
   C) Email
   D) SSH
   **Why:** Prometheus pulls from `/metrics` endpoints.

7. Which is used to parse JSON in bash?
   A) sed
   B) jq ✅
   C) awk
   D) cut
   **Why:** `jq` specializes in JSON.

8. Command to view live logs of Kubernetes pod:
   A) kubectl get pods
   B) kubectl logs -f podname ✅
   C) docker ps
   D) systemctl status
   **Why:** `kubectl logs -f` follows logs.

9. In CI/CD, a canary deployment means:
   A) Deploy to all users at once
   B) Deploy to small subset then observe ✅
   C) Never deploy
   D) Only manual deploys
   **Why:** Canary reduces blast radius.

10. For infrastructure as code (IaC), Terraform uses:
    A) YAML only
    B) HCL (HashiCorp Config Language) ✅
    C) JSON only
    D) Perl
    **Why:** Terraform uses HCL.

11. Which command shows top 5 processes by memory in one-liner?
    A) ps aux --sort=-%mem | head -n 6 ✅
    B) top -b | head
    C) free -m
    D) df -h
    **Why:** Sorted `ps` lists top memory consumers.

12. Which is best for centralized logging?
    A) Prometheus
    B) Elasticsearch + Kibana (ELK) ✅
    C) Grafana only
    D) GitHub
    **Why:** ELK stack aggregates and searches logs.

13. In Ansible, `--check` flag does:
    A) Execute changes
    B) No-op/dry-run to show changes ✅
    C) Delete inventory
    D) Reset host
    **Why:** `--check` previews changes.

14. To avoid secrets in logs, you should:
    A) Print secrets everywhere
    B) Mask secrets in CI and use secret managers ✅
    C) Store secrets in code
    D) Email secrets
    **Why:** Secrets managers and masking prevent leakage.

15. A good alert should be:
    A) Noisy and frequent
    B) Actionable with runbook ✅
    C) Ambiguous
    D) Ignoreable
    **Why:** Alerts must lead to clear action.

---

# Topic G — SRE Principles & Reliability (15 MCQs)

1. SLI stands for:
   A) Service Level Indicator ✅
   B) Service Level Init
   C) System Level Index
   D) Service Loss Indicator
   **Why:** SLI = measurable metric.

2. SLO is:
   A) Service Level Objective (target) ✅
   B) Service Level Obligation
   C) Service Loss Option
   D) System Log Output
   **Why:** SLO sets target for SLI.

3. Error budget equals:
   A) SLO value
   B) 100% - SLO (allowed error fraction) ✅
   C) SLA penalty
   D) Downtime only
   **Why:** Error budget is allowable failure margin.

4. Blameless postmortem means:
   A) No accountability
   B) Focus on root causes and fixes without finger-pointing ✅
   C) Never fix issues
   D) Ignore incidents
   **Why:** Blamelessness encourages learning.

5. Which is a pillar of observability?
   A) Metrics, logs, traces ✅
   B) Only metrics
   C) Only logs
   D) None
   **Why:** Observability uses all three.

6. Which alerting strategy reduces paging for minor issues?
   A) Page on any event
   B) Use SLO-based thresholds and severity tiers ✅
   C) Page only at midnight
   D) Never page
   **Why:** SLO-based alerts reduce unnecessary pages.

7. Incident triage order:
   A) Recover → Notify → Triage
   B) Triage → Mitigate → Collect evidence → Communicate ✅
   C) Communicate → Mitigate only
   D) Ignore
   **Why:** Structured response ensures controlled action.

8. To reduce toil, SRE should:
   A) Automate repetitive tasks ✅
   B) Do more manual work
   C) Avoid automation
   D) Replace team members
   **Why:** Automation reduces manual repetitive effort.

9. What is a runbook?
   A) A music album
   B) Ordered operational steps to handle incidents ✅
   C) A code repository
   D) A monitoring dashboard
   **Why:** Runbook gives step-by-step ops actions.

10. On-call best practice includes:
    A) No handover
    B) Clear handover notes and runbook access ✅
    C) Ignore previous context
    D) Random restarts
    **Why:** Handover prevents lost context.

11. Postmortem should include:
    A) Timeline, root cause, action items ✅
    B) Only blame
    C) Joke summary
    D) No details
    **Why:** Postmortem drives improvements.

12. Burn rate measures:
    A) Rate of error budget consumption ✅
    B) CPU burn
    C) Disk write speed
    D) Log volume
    **Why:** Burn rate shows how fast error budget is used.

13. Which metric to alert on for latency SLO?
    A) p99 latency ✅
    B) total requests only
    C) CPU only
    D) disk model
    **Why:** p99 reflects worst-case latency impacting users.

14. If error budget is nearly exhausted, you should:
    A) Pause risky launches and focus on reliability ✅
    B) Launch more changes
    C) Ignore it
    D) Delete alerts
    **Why:** Error budget governs pace of change.

15. What is toil?
    A) Meaningful, creative work
    B) Manual, repetitive operational work that scales linearly with service size ✅
    C) Automated pipeline
    D) Performance test
    **Why:** Toil is repetitive low-value manual work to be automated.

---



