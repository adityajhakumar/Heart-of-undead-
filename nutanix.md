
## YouTube Videos by Topic and Study Day

[Introduction to Linux – Full Course for Beginners](https://www.youtube.com/watch?v=sWbUDq4S6Y8&utm_source=chatgpt.com)

**Topic:** Linux & OS Fundamentals — essential commands, file system, process/memory management.
**Suggested Day:** **Friday**

[Introduction to Networking | Network Fundamentals Part 1](https://www.youtube.com/watch?v=9SIjoeE93lo&utm_source=chatgpt.com)

**Topic:** Networking Basics — TCP/IP, DNS, ports, basic tools.
**Suggested Day:** **Friday**

[Containers vs VMs: What's the difference?](https://www.youtube.com/watch?v=cjXI-yxqGTI&utm_source=chatgpt.com)

**Topic:** Virtualization vs Containerization — key differences and use cases.
**Suggested Day:** **Saturday**

[Linux Server Troubleshooting](https://www.youtube.com/watch?v=2YOiJGSzUKI&utm_source=chatgpt.com)

**Topic:** Troubleshooting Linux Servers — methods and log usage.
**Suggested Day:** **Saturday**

[How It Works | Nutanix](https://www.youtube.com/watch?v=iTRrB8ck0fM&utm_source=chatgpt.com)

**Topic:** Nutanix HCI Basics — what hyperconverged infrastructure is and Nutanix’s approach.
**Suggested Day:** **Saturday**

[Bash Scripting Tutorial for Beginners](https://www.youtube.com/watch?v=tK9Oc6AEnR4&utm_source=chatgpt.com)

**Topic:** Bash Scripting Crash Course — scripting basics for automation.
**Suggested Day:** **Saturday**

[What Is Computer Hardware ? | Beginners Guide To ...](https://www.youtube.com/watch?v=8_itvuz5Dc4&utm_source=chatgpt.com)

**Topic:** Computer Hardware Basics — CPU, RAM, storage, motherboard overview.
**Suggested Day:** **Friday**

---

## Study Schedule Outline

| **Day**      | **Morning Session**                                        | **Afternoon Session**                                         | **Evening Session**                                     |
| ------------ | ---------------------------------------------------------- | ------------------------------------------------------------- | ------------------------------------------------------- |
| **Friday**   | Linux fundamentals video + practice (commands, logs, etc.) | Networking basics video + hands-on (`ping`, `nslookup`, etc.) | Hardware basics video + quick recap                     |
| **Saturday** | Virtualization vs containers video + comparison notes      | Nutanix HCI overview video and takeaways                      | Troubleshooting video + bash scripting video + practice |

---

## Comprehensive Study Topics Table

| **Category**                     | **Core Topics to Cover**                                                                                                              | **Included Video?** |
| -------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------- | ------------------- |
| **Linux & OS Fundamentals**      | Essential commands (`ps`, `top`, `df`, `du`, `free`, `systemctl`, `journalctl`), process vs thread, paging/swapping, file permissions | Yes (Friday)        |
| **Networking Fundamentals**      | TCP/IP, DNS, HTTP/HTTPS, ports (e.g., 22, 80, 443), tools (`ping`, `traceroute`, `nslookup`, `curl`)                                  | Yes (Friday)        |
| **Hardware Basics**              | CPU, RAM, storage (SSD vs HDD), motherboard, failure impact                                                                           | Yes (Friday)        |
| **Virtualization vs Containers** | Hypervisor vs container models, Type 1 vs Type 2, use cases                                                                           | Yes (Saturday)      |
| **Nutanix HCI & Cloud Concepts** | Hyperconverged Infrastructure (HCI), Nutanix AOS/Prism/AHV overview, hybrid cloud context                                             | Yes (Saturday)      |
| **Troubleshooting Approach**     | Log analysis, connectivity checks, resource monitoring, root cause vs temporary fix                                                   | Yes (Saturday)      |
| **Scripting (Bash)**             | Basic scripting (loops, conditionals, file I/O, grep/awk/sed usage for log parsing and automation)                                    | Yes (Saturday)      |
| **Communication & Soft Skills**  | Structure your thought process, talk through steps clearly, use customer-focused troubleshooting language                             | Practice both days  |

---

### Final Prep Tips

* **Start with the videos** during your study times as suggested in the schedule.
* **Practice alongside** the videos—type commands, troubleshoot sample issues, write simple scripts.
* **At the end of each day**, spend 15 minutes summarizing:

  * What you learned
  * Key commands or definitions
  * One scenario-based question to self-test
* **Before the interview**, rehearse explaining:

  * “How would you debug a Linux server that’s unresponsive?”
  * “Difference between containers and virtual machines?”
  * “What is Nutanix’s HCI and why does it matter?”
* Use the table as a checklist—see what’s covered and review any missed topics.



### ✅ Coverage Check vs Nutanix JD

| **Requirement from JD**             | **Covered by Videos / Table**                                                                                                                  |
| ----------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------- |
| **Operating Systems (Linux focus)** | ✔ [Linux Fundamentals](https://www.youtube.com/watch?v=sWbUDq4S6Y8&utm_source=chatgpt.com) + Troubleshooting video                             |
| **Networking**                      | ✔ [Networking Basics](https://www.youtube.com/watch?v=9SIjoeE93lo&utm_source=chatgpt.com)                                                      |
| **Hardware**                        | ✔ [Computer Hardware Basics](https://www.youtube.com/watch?v=8_itvuz5Dc4&utm_source=chatgpt.com)                                               |
| **Virtualization**                  | ✔ [Containers vs VMs](https://www.youtube.com/watch?v=cjXI-yxqGTI&utm_source=chatgpt.com)                                                      |
| **Cloud / HCI / Nutanix stack**     | ✔ [Nutanix HCI Basics](https://www.youtube.com/watch?v=iTRrB8ck0fM&utm_source=chatgpt.com)                                                     |
| **Troubleshooting / Debugging**     | ✔ [Linux Server Troubleshooting](https://www.youtube.com/watch?v=2YOiJGSzUKI&utm_source=chatgpt.com)                                           |
| **Scripting (Bash/Python)**         | ✔ [Bash Scripting Tutorial](https://www.youtube.com/watch?v=tK9Oc6AEnR4&utm_source=chatgpt.com) (Python basics you can skim from docs if time) |
| **Customer-Centric Communication**  | ✔ Not a video, but included in your prep plan (mock Q\&A, structured explanations)                                                             |

---







# 🐧 20 MCQs — Linux & Operating Systems (with Answers)

**Q1.** Which command is used to check memory usage in Linux?
a) `df -h`
b) `free -m`
c) `ps aux`
d) `top`
**Ans:** b) `free -m`

**Q2.** What does `chmod 755 file.txt` do?
a) Read-only for all
b) Full access to all
c) Owner: read/write/execute; Group & Others: read/execute
d) Group only can write
**Ans:** c) Owner: read/write/execute; Group & Others: read/execute

**Q3.** Which process in Linux has PID 1?
a) `bash`
b) `init`/`systemd`
c) `cron`
d) `kernel`
**Ans:** b) `init`/`systemd`

**Q4.** Which command shows running processes with CPU and memory usage?
a) `du -sh`
b) `ps aux`
c) `ls -l`
d) `free`
**Ans:** b) `ps aux`

**Q5.** What does `df -h` display?
a) Disk usage in MB
b) Memory usage in KB
c) File permissions
d) Disk space usage in human-readable format
**Ans:** d) Disk space usage in human-readable format

**Q6.** In Linux, what is a “zombie process”?
a) Process waiting for I/O
b) Terminated process still in process table
c) Long-running process
d) Hung process
**Ans:** b) Terminated process still in process table

**Q7.** Which file contains user account details?
a) `/etc/hosts`
b) `/etc/shadow`
c) `/etc/passwd`
d) `/etc/group`
**Ans:** c) `/etc/passwd`

**Q8.** Which command kills a process by name?
a) `kill`
b) `pkill`
c) `stop`
d) `end`
**Ans:** b) `pkill`

**Q9.** Swap space in Linux is used for:
a) Disk caching
b) Extending RAM when it’s full
c) Storing logs
d) Backups
**Ans:** b) Extending RAM when it’s full

**Q10.** The `cron` daemon is used for:
a) Process monitoring
b) Scheduling recurring tasks
c) Log rotation
d) User authentication
**Ans:** b) Scheduling recurring tasks

**Q11.** To check system uptime, you use:
a) `whoami`
b) `uptime`
c) `w`
d) `uname`
**Ans:** b) `uptime`

**Q12.** What does the command `tail -f /var/log/syslog` do?
a) Shows first 10 lines of syslog
b) Shows last 10 lines of syslog and updates live
c) Deletes syslog
d) Filters syslog by “f”
**Ans:** b) Shows last 10 lines of syslog and updates live

**Q13.** Which signal is sent by default using `kill <PID>`?
a) SIGSTOP
b) SIGINT
c) SIGTERM
d) SIGKILL
**Ans:** c) SIGTERM

**Q14.** In Linux permissions, what does `-rw-r--r--` mean?
a) Owner: read/write, Group: read, Others: read
b) Owner: execute only
c) Everyone: read/write
d) Owner: read only
**Ans:** a) Owner: read/write, Group: read, Others: read

**Q15.** Which command lists open files and the processes using them?
a) `ls`
b) `top`
c) `lsof`
d) `free`
**Ans:** c) `lsof`

**Q16.** What does `systemctl restart nginx` do?
a) Installs nginx
b) Restarts the nginx service
c) Kills nginx
d) Displays nginx logs
**Ans:** b) Restarts the nginx service

**Q17.** Which Linux runlevel means “multi-user mode with networking”?
a) 0
b) 1
c) 3
d) 5
**Ans:** c) 3

**Q18.** Which log file stores system boot messages?
a) `/var/log/syslog`
b) `/var/log/dmesg`
c) `/var/log/boot.log`
d) `/var/log/messages`
**Ans:** b) `/var/log/dmesg`

**Q19.** What is the difference between `hard link` and `soft link` in Linux?
a) Hard link points to file data, soft link points to file path
b) Soft link copies the file, hard link does not
c) Hard link is read-only, soft link is editable
d) No difference
**Ans:** a) Hard link points to file data, soft link points to file path

**Q20.** Which Linux directory contains configuration files?
a) `/etc`
b) `/bin`
c) `/home`
d) `/usr`
**Ans:** a) `/etc`

---



# 🌐 20 MCQs — Networking (with Answers)

**Q1.** Which layer of the OSI model does IP operate on?
a) Data Link
b) Network
c) Transport
d) Application
**Ans:** b) Network

**Q2.** Which command is used to test basic connectivity between two hosts?
a) `nslookup`
b) `ping`
c) `curl`
d) `netstat`
**Ans:** b) `ping`

**Q3.** What is the default port for HTTPS?
a) 21
b) 22
c) 80
d) 443
**Ans:** d) 443

**Q4.** Which protocol does DNS use primarily?
a) TCP
b) UDP
c) ICMP
d) HTTP
**Ans:** b) UDP

**Q5.** What does `traceroute` do?
a) Tests bandwidth
b) Lists all hops between source and destination
c) Displays open ports
d) Resolves DNS names
**Ans:** b) Lists all hops between source and destination

**Q6.** Which command shows all active network connections in Linux?
a) `ip addr`
b) `ping`
c) `netstat -tulnp`
d) `dig`
**Ans:** c) `netstat -tulnp`

**Q7.** TCP is:
a) Connectionless and unreliable
b) Connection-oriented and reliable
c) Connectionless and reliable
d) None of the above
**Ans:** b) Connection-oriented and reliable

**Q8.** Which protocol is used to transfer files securely?
a) FTP
b) HTTP
c) SFTP
d) SMTP
**Ans:** c) SFTP

**Q9.** What does NAT (Network Address Translation) do?
a) Converts MAC addresses
b) Maps private IPs to public IPs
c) Assigns DNS names
d) Encrypts packets
**Ans:** b) Maps private IPs to public IPs

**Q10.** Which DNS record type is used to map a domain name to an IPv4 address?
a) MX
b) A
c) AAAA
d) CNAME
**Ans:** b) A

**Q11.** Which protocol does the command `ssh` use?
a) TCP
b) UDP
c) ICMP
d) ARP
**Ans:** a) TCP

**Q12.** Which port is used by SSH?
a) 21
b) 22
c) 25
d) 110
**Ans:** b) 22

**Q13.** Which of these is a Layer 2 device?
a) Switch
b) Router
c) Firewall
d) Load balancer
**Ans:** a) Switch

**Q14.** What does ARP do?
a) Resolves IP addresses to MAC addresses
b) Resolves MAC addresses to IP addresses
c) Resolves URLs to IP addresses
d) Resolves ports to services
**Ans:** a) Resolves IP addresses to MAC addresses

**Q15.** Which protocol is used to send emails?
a) HTTP
b) FTP
c) SMTP
d) POP3
**Ans:** c) SMTP

**Q16.** What does the command `dig google.com` do?
a) Tests connectivity
b) Resolves DNS information for google.com
c) Shows routing table
d) Displays logs
**Ans:** b) Resolves DNS information for google.com

**Q17.** What is the size of an IPv4 address?
a) 32 bits
b) 64 bits
c) 128 bits
d) 256 bits
**Ans:** a) 32 bits

**Q18.** Which protocol is commonly used for streaming video/audio?
a) TCP
b) UDP
c) SMTP
d) SSH
**Ans:** b) UDP

**Q19.** Which HTTP response code means “Not Found”?
a) 200
b) 301
c) 403
d) 404
**Ans:** d) 404

**Q20.** You cannot `ping` a server, but `ssh` works fine. What is the most likely reason?
a) Server is down
b) ICMP blocked by firewall
c) DNS not working
d) Port 22 is closed
**Ans:** b) ICMP blocked by firewall

---





# 🖥️ 20 MCQs — Virtualization & Containers (with Answers)

**Q1.** What is a hypervisor?
a) A type of container runtime
b) A program that creates and manages VMs
c) A cloud storage system
d) A network firewall
**Ans:** b) A program that creates and manages VMs

**Q2.** Which is a **Type 1 hypervisor** (bare metal)?
a) VMware ESXi
b) VirtualBox
c) VMware Workstation
d) QEMU
**Ans:** a) VMware ESXi

**Q3.** Which Nutanix product is its native hypervisor?
a) Prism
b) AHV
c) AOS
d) Calm
**Ans:** b) AHV

**Q4.** Containers are:
a) Full OS with kernel
b) Lightweight, share the host OS kernel
c) Hardware virtualization
d) Always slower than VMs
**Ans:** b) Lightweight, share the host OS kernel

**Q5.** Which of these is a container runtime?
a) Docker
b) VMware ESXi
c) Citrix XenServer
d) AHV
**Ans:** a) Docker

**Q6.** Which Nutanix product provides the management interface for infrastructure?
a) Prism
b) Calm
c) Karbon
d) Xi
**Ans:** a) Prism

**Q7.** Which advantage do containers have over VMs?
a) Better isolation
b) Faster startup and less resource usage
c) Hardware-level virtualization
d) Require more memory
**Ans:** b) Faster startup and less resource usage

**Q8.** Which technology does Nutanix use to combine compute, storage, and networking into one platform?
a) Virtual Private Cloud
b) Hyperconverged Infrastructure (HCI)
c) Software Defined Networking
d) Cloud Federation
**Ans:** b) Hyperconverged Infrastructure (HCI)

**Q9.** Which of these is an example of Type 2 hypervisor?
a) KVM
b) Hyper-V
c) VirtualBox
d) VMware ESXi
**Ans:** c) VirtualBox

**Q10.** What is an image in Docker?
a) A snapshot of a VM
b) A lightweight, immutable file system with application + dependencies
c) A virtual disk
d) A storage driver
**Ans:** b) A lightweight, immutable file system with application + dependencies

**Q11.** What is “container orchestration”?
a) Managing CPU allocation for VMs
b) Automated deployment, scaling, and management of containers
c) Splitting one VM into many
d) Adding RAID storage to containers
**Ans:** b) Automated deployment, scaling, and management of containers

**Q12.** Which orchestration platform is most widely used?
a) Kubernetes
b) OpenStack
c) vSphere
d) Docker Compose
**Ans:** a) Kubernetes

**Q13.** In Nutanix, what does AOS stand for?
a) Acropolis Operating System
b) Advanced Orchestration Service
c) Application Orchestration System
d) Automated OS
**Ans:** a) Acropolis Operating System

**Q14.** Which feature is common to **both VMs and containers**?
a) Each runs its own kernel
b) Isolation of applications
c) Boot time in milliseconds
d) Hardware pass-through always enabled
**Ans:** b) Isolation of applications

**Q15.** Which is a limitation of containers compared to VMs?
a) Containers cannot run different OS kernels from the host
b) Containers use more memory
c) Containers boot slower
d) Containers cannot be scaled
**Ans:** a) Containers cannot run different OS kernels from the host

**Q16.** Which Nutanix product integrates Kubernetes for container management?
a) Calm
b) Karbon
c) AHV
d) Files
**Ans:** b) Karbon

**Q17.** What does “VM snapshot” mean?
a) A backup of system logs only
b) A copy of VM memory and disk state at a given point in time
c) A container image build
d) A log rotation tool
**Ans:** b) A copy of VM memory and disk state at a given point in time

**Q18.** Which is **NOT** a benefit of virtualization?
a) Consolidation of servers
b) Easier scaling of applications
c) Running multiple OSes on one machine
d) 100% elimination of hardware failures
**Ans:** d) 100% elimination of hardware failures

**Q19.** In virtualization, what is “live migration”?
a) Moving a VM from one host to another without downtime
b) Converting a container into a VM
c) Restarting a VM with higher resources
d) Copying VM data to backup
**Ans:** a) Moving a VM from one host to another without downtime

**Q20.** Which Nutanix hypervisor is based on KVM?
a) Prism
b) AHV
c) AOS
d) Calm
**Ans:** b) AHV

---


# ☁️ 20 MCQs — Cloud & Nutanix HCI (with Answers)

**Q1.** What is the main idea of Hyperconverged Infrastructure (HCI)?
a) Separate compute, storage, and networking
b) Combine compute, storage, and networking into a single software-defined platform
c) Hardware-only virtualization
d) Container-only infrastructure
**Ans:** b) Combine compute, storage, and networking into a single software-defined platform

**Q2.** Nutanix’s core software platform is called:
a) Prism
b) AOS
c) Calm
d) Beam
**Ans:** b) AOS

**Q3.** Which component in Nutanix provides **management and monitoring**?
a) AHV
b) Prism
c) Karbon
d) Era
**Ans:** b) Prism

**Q4.** Which hypervisor is built into Nutanix AOS?
a) VMware ESXi
b) Hyper-V
c) AHV (Acropolis Hypervisor)
d) KVM only
**Ans:** c) AHV (Acropolis Hypervisor)

**Q5.** Nutanix’s solution for Kubernetes and containers is:
a) Calm
b) Karbon
c) Beam
d) Era
**Ans:** b) Karbon

**Q6.** What does “cloud-native” mean?
a) Applications that require physical servers only
b) Applications designed for scalability, microservices, and containers in cloud environments
c) Applications without networking
d) Legacy monolithic applications
**Ans:** b) Applications designed for scalability, microservices, and containers in cloud environments

**Q7.** Which is an example of **IaaS** (Infrastructure as a Service)?
a) AWS EC2
b) Microsoft Office 365
c) Salesforce CRM
d) GitHub
**Ans:** a) AWS EC2

**Q8.** Which Nutanix product is used for **database-as-a-service automation**?
a) Calm
b) Era
c) Prism
d) Xi
**Ans:** b) Era

**Q9.** Which Nutanix product provides **cost optimization and governance** for multi-cloud?
a) Beam
b) Calm
c) AOS
d) Xi Frame
**Ans:** a) Beam

**Q10.** In Nutanix, which service enables **automation and orchestration** (app blueprints, workflows)?
a) Karbon
b) Prism
c) Calm
d) Xi Leap
**Ans:** c) Calm

**Q11.** Which is NOT a typical advantage of cloud computing?
a) On-demand scalability
b) Pay-as-you-go pricing
c) Vendor lock-in avoidance guaranteed
d) Global access
**Ans:** c) Vendor lock-in avoidance guaranteed

**Q12.** Which Nutanix service provides **DRaaS (Disaster Recovery as a Service)**?
a) Xi Leap
b) Calm
c) Era
d) Karbon
**Ans:** a) Xi Leap

**Q13.** Nutanix was a pioneer in:
a) Hyperconverged Infrastructure
b) Public Cloud
c) Antivirus software
d) Networking hardware
**Ans:** a) Hyperconverged Infrastructure

**Q14.** In cloud terms, “hybrid cloud” means:
a) Private cloud only
b) Public cloud only
c) A mix of private and public cloud environments
d) Edge-only computing
**Ans:** c) A mix of private and public cloud environments

**Q15.** Nutanix’s SaaS platform for running virtual desktops in the cloud is:
a) Xi Leap
b) Xi Frame
c) Calm
d) Era
**Ans:** b) Xi Frame

**Q16.** Which cloud model gives customers **control of OS, applications, but not hardware**?
a) IaaS
b) PaaS
c) SaaS
d) FaaS
**Ans:** a) IaaS

**Q17.** Which protocol is commonly used in cloud storage access?
a) NFS
b) iSCSI
c) S3 API
d) FTP
**Ans:** c) S3 API

**Q18.** Nutanix **Prism Central** is different from Prism Element because it:
a) Manages multiple Nutanix clusters centrally
b) Works only on single cluster
c) Runs only in VMware
d) Is used only for databases
**Ans:** a) Manages multiple Nutanix clusters centrally

**Q19.** Which is a key Nutanix advantage over traditional infrastructure?
a) Hardware-only approach
b) Requires external SAN storage always
c) Simplified scale-out architecture
d) Runs only on VMware
**Ans:** c) Simplified scale-out architecture

**Q20.** Nutanix supports running workloads on:
a) Only AHV
b) VMware ESXi, Hyper-V, AHV, and more
c) Only VMware
d) Only AWS
**Ans:** b) VMware ESXi, Hyper-V, AHV, and more

---


# 🔧 20 MCQs — Troubleshooting & SRE Concepts (with Answers)

**Q1.** What does SRE stand for?
a) Systems Recovery Engineer
b) Site Reliability Engineer
c) Systems Reliability Expert
d) Software Runtime Engineer
**Ans:** b) Site Reliability Engineer

**Q2.** In monitoring, what does an **alert** usually mean?
a) A user ticket is closed
b) A metric has crossed a predefined threshold
c) Logs have been deleted
d) Network is always down
**Ans:** b) A metric has crossed a predefined threshold

**Q3.** Which command in Linux shows the **last 50 lines of logs in real-time**?
a) `cat logfile`
b) `grep logfile`
c) `tail -n 50 -f logfile`
d) `head -n 50 logfile`
**Ans:** c) `tail -n 50 -f logfile`

**Q4.** In troubleshooting, the “root cause analysis” means:
a) Guessing the issue
b) Identifying the fundamental reason for a failure
c) Escalating immediately
d) Restarting the service
**Ans:** b) Identifying the fundamental reason for a failure

**Q5.** Which is NOT a common Linux troubleshooting tool?
a) `top`
b) `ps`
c) `ping`
d) `msword`
**Ans:** d) `msword`

**Q6.** Which metric measures **availability** in SRE terms?
a) SLI (Service Level Indicator)
b) SLA (Service Level Agreement)
c) SLO (Service Level Objective)
d) MTTR (Mean Time To Recovery)
**Ans:** a) SLI (Service Level Indicator)

**Q7.** The agreed target availability with customers is defined in:
a) SLA
b) MTBF
c) Log files
d) Alert threshold
**Ans:** a) SLA

**Q8.** Which log is used to see **system events** in Linux?
a) `/var/log/syslog` (Ubuntu/Debian)
b) `/var/log/system.log` (macOS)
c) `/var/log/messages` (RHEL/CentOS)
d) All of the above
**Ans:** d) All of the above

**Q9.** The metric **MTTR** stands for:
a) Maximum Time To Resolve
b) Mean Time To Recovery/Repair
c) Minimum Troubleshooting Runtime
d) Mean Time To Restart
**Ans:** b) Mean Time To Recovery/Repair

**Q10.** You get a **CPU spike alert**. Which Linux command helps identify the process consuming most CPU?
a) `df -h`
b) `top`
c) `free -m`
d) `ls -l`
**Ans:** b) `top`

**Q11.** Which tool is commonly used for **log collection & visualization** in SRE?
a) GitHub
b) ELK stack (Elasticsearch, Logstash, Kibana)
c) Photoshop
d) MS Excel
**Ans:** b) ELK stack (Elasticsearch, Logstash, Kibana)

**Q12.** Which of these is an **observability pillar**?
a) Logs
b) Metrics
c) Traces
d) All of the above
**Ans:** d) All of the above

**Q13.** When troubleshooting a **network issue**, which command checks if the server is listening on a port?
a) `ping`
b) `netstat -tulnp`
c) `df -h`
d) `uptime`
**Ans:** b) `netstat -tulnp`

**Q14.** Which approach is best when resolving customer issues?
a) Restart everything immediately
b) Blame the network
c) Follow systematic troubleshooting and document findings
d) Escalate without checking logs
**Ans:** c) Follow systematic troubleshooting and document findings

**Q15.** Which monitoring tool is widely used in SRE/DevOps?
a) Prometheus + Grafana
b) MS Paint
c) Eclipse IDE
d) Thunderbird
**Ans:** a) Prometheus + Grafana

**Q16.** “Error budget” in SRE means:
a) Maximum cost allowed for hardware
b) Maximum acceptable downtime/failures before SLA is breached
c) Extra logging quota
d) Free cloud credits
**Ans:** b) Maximum acceptable downtime/failures before SLA is breached

**Q17.** A service is **slow**. First step to troubleshoot?
a) Restart server blindly
b) Check resource usage (CPU, memory, disk, network)
c) Delete logs
d) Call vendor immediately
**Ans:** b) Check resource usage (CPU, memory, disk, network)

**Q18.** Which Linux command shows **disk usage**?
a) `du -sh *`
b) `df -h`
c) Both a & b
d) `ls -la`
**Ans:** c) Both a & b

**Q19.** Which practice improves **system reliability**?
a) Chaos Engineering (resiliency testing)
b) Ignoring alerts
c) Manual interventions only
d) Skipping documentation
**Ans:** a) Chaos Engineering (resiliency testing)

**Q20.** A customer reports a service outage. What is the **best SRE response**?
a) Stay silent
b) Acknowledge, communicate status, start triaging immediately
c) Wait until more complaints come
d) Blame another team
**Ans:** b) Acknowledge, communicate status, start triaging immediately

---


# 💻 20 MCQs — Hardware Basics for SRE (with Answers)

**Q1.** Which component is considered the **“brain”** of a computer system?
a) RAM
b) CPU
c) Disk
d) NIC
**Ans:** b) CPU

**Q2.** What does RAM stand for?
a) Random Assignment Memory
b) Read Access Memory
c) Random Access Memory
d) Real Allocation Module
**Ans:** c) Random Access Memory

**Q3.** Which storage type is **faster but more expensive**?
a) HDD
b) SSD
c) Tape drive
d) Optical disk
**Ans:** b) SSD

**Q4.** IOPS refers to:
a) Internet Operations Per Second
b) Input/Output Processes
c) Input/Output Operations Per Second
d) Internal OS Processing Speed
**Ans:** c) Input/Output Operations Per Second

**Q5.** Latency in storage refers to:
a) Total capacity of disk
b) Delay between request and response
c) Bandwidth usage
d) CPU speed
**Ans:** b) Delay between request and response

**Q6.** RAID 0 provides:
a) Mirroring only
b) Striping without redundancy (performance, no fault tolerance)
c) Full fault tolerance
d) Parity-based fault tolerance
**Ans:** b) Striping without redundancy

**Q7.** RAID 1 provides:
a) Striping only
b) Mirroring (full redundancy, no striping)
c) Parity with performance
d) No redundancy
**Ans:** b) Mirroring

**Q8.** RAID 5 requires a minimum of:
a) 2 disks
b) 3 disks
c) 4 disks
d) 5 disks
**Ans:** b) 3 disks

**Q9.** Which RAID level combines **striping + mirroring**?
a) RAID 0
b) RAID 1
c) RAID 5
d) RAID 10
**Ans:** d) RAID 10

**Q10.** ECC memory is used to:
a) Speed up disk I/O
b) Detect and correct memory errors
c) Encrypt CPU instructions
d) Increase network speed
**Ans:** b) Detect and correct memory errors

**Q11.** Which storage type is most commonly used in **cloud data centers**?
a) Tape drives
b) SSDs
c) Floppy disks
d) DVDs
**Ans:** b) SSDs

**Q12.** What does CPU clock speed measure?
a) Instructions per cycle
b) Frequency of cycles per second (GHz)
c) Cache size
d) Number of cores
**Ans:** b) Frequency of cycles per second (GHz)

**Q13.** Which CPU cache level is **largest but slowest**?
a) L1
b) L2
c) L3
d) RAM
**Ans:** c) L3

**Q14.** Which type of disk has **moving parts**?
a) HDD
b) SSD
c) NVMe
d) RAM
**Ans:** a) HDD

**Q15.** Which is a benefit of **NVMe storage** compared to SATA SSDs?
a) Lower latency & higher throughput via PCIe
b) More moving parts
c) Larger physical size
d) Works only with HDDs
**Ans:** a) Lower latency & higher throughput via PCIe

**Q16.** Which component determines how many programs can run **in parallel**?
a) CPU clock speed
b) CPU cores
c) Disk IOPS
d) NIC bandwidth
**Ans:** b) CPU cores

**Q17.** Which is typically **volatile memory** (lost when power is off)?
a) SSD
b) RAM
c) HDD
d) NVMe
**Ans:** b) RAM

**Q18.** A server has 4 HDDs in RAID 5. If one disk fails:
a) All data is lost immediately
b) System continues to work with degraded performance
c) Mirroring keeps data safe without performance loss
d) RAID auto-upgrades to RAID 10
**Ans:** b) System continues to work with degraded performance

**Q19.** The **main bottleneck** in legacy systems with separate storage (SAN) vs. HCI is usually:
a) CPU
b) Network/storage latency
c) RAM
d) Number of CPU cores
**Ans:** b) Network/storage latency

**Q20.** In HCI (like Nutanix), local disks are pooled into:
a) SAN arrays
b) A distributed storage fabric
c) RAID 0 only
d) Network-only storage
**Ans:** b) A distributed storage fabric

---




# 🐚 10 Bash Scripting Tasks (with Solutions)

**Q1.** Write a command to display the current working directory.
✅ `pwd`

**Q2.** Count the number of files in the current directory.
✅ `ls -1 | wc -l`

**Q3.** Find the top 5 processes consuming the most memory.
✅ `ps aux --sort=-%mem | head -n 6`

**Q4.** Write a script to check if a file exists.

```bash
#!/bin/bash
if [ -f "$1" ]; then
  echo "File exists"
else
  echo "File does not exist"
fi
```

**Q5.** Extract all lines containing “error” from a log file.
✅ `grep -i "error" logfile.log`

**Q6.** Display disk usage in human-readable format.
✅ `df -h`

**Q7.** Write a script that pings google.com 3 times and reports if it’s reachable.

```bash
#!/bin/bash
if ping -c 3 google.com > /dev/null; then
  echo "Google is reachable"
else
  echo "Google is NOT reachable"
fi
```

**Q8.** Show the last 20 lines of `/var/log/syslog`.
✅ `tail -n 20 /var/log/syslog`

**Q9.** List all open ports on the system.
✅ `netstat -tulnp`

**Q10.** Write a one-liner to print numbers 1 to 5.
✅ `for i in {1..5}; do echo $i; done`

---

# 🐍 10 Python Scripting Tasks (with Solutions)

**Q1.** Print “Hello Nutanix” in Python.

```python
print("Hello Nutanix")
```

**Q2.** Read a file and print its contents.

```python
with open("file.txt") as f:
    print(f.read())
```

**Q3.** Count the number of lines in a file.

```python
with open("file.txt") as f:
    print(sum(1 for line in f))
```

**Q4.** Print the current date and time.

```python
import datetime
print(datetime.datetime.now())
```

**Q5.** Parse a log file and print lines containing “ERROR”.

```python
with open("log.txt") as f:
    for line in f:
        if "ERROR" in line:
            print(line.strip())
```

**Q6.** Write a function that checks if a number is prime.

```python
def is_prime(n):
    if n < 2:
        return False
    for i in range(2, int(n**0.5)+1):
        if n % i == 0:
            return False
    return True
```

**Q7.** Script to ping google.com (simulate with system call).

```python
import os
response = os.system("ping -c 1 google.com")
print("Reachable" if response == 0 else "Not reachable")
```

**Q8.** Print top 3 numbers from a list.

```python
nums = [5, 2, 9, 1, 7]
print(sorted(nums, reverse=True)[:3])
```

**Q9.** Create a dictionary of word counts from a string.

```python
text = "nutanix nutanix cloud sre"
counts = {}
for word in text.split():
    counts[word] = counts.get(word, 0) + 1
print(counts)
```

**Q10.** Connect to an API (mock) and print status code.

```python
import requests
resp = requests.get("https://httpbin.org/get")
print(resp.status_code)
```

---


# 🗣️ **Soft Skills / Customer-first Scenarios (10 Qs)**

*(No fixed answers — but here’s what they look for)*

**Q1.** A customer reports their Nutanix cluster is down and is frustrated. How do you respond?
👉 Stay calm, acknowledge urgency, reassure them, gather logs, escalate if needed.

**Q2.** How would you explain “hyperconverged infrastructure” to a non-technical customer?
👉 Use simple analogy: “Instead of separate boxes for compute, storage, and networking, Nutanix combines them into one powerful system, like a smartphone combines camera + phone + computer.”

**Q3.** A colleague disagrees with your troubleshooting approach. What do you do?
👉 Collaborate, listen, justify with facts, align on customer-first resolution.

**Q4.** Customer says: “We already tried your solution and it didn’t work.” What’s your next step?
👉 Ask clarifying questions, review logs, double-check steps, escalate if reproducible.

**Q5.** Customer asks something you don’t know. Do you guess?
👉 Admit you don’t know but will check with engineering and get back quickly.

**Q6.** If multiple customers face high-priority issues at once, how do you prioritize?
👉 Follow severity/impact guidelines (production outage > minor bug).

**Q7.** A customer keeps interrupting while you’re troubleshooting. How do you handle it?
👉 Politely acknowledge, assure progress updates, then continue systematic troubleshooting.

**Q8.** How would you calm an angry customer?
👉 Stay professional, empathize, avoid blame, show urgency to solve.

**Q9.** Your fix solves the issue temporarily but not permanently. Do you stop there?
👉 No — document RCA, suggest permanent fix, open bug report if needed.

**Q10.** What does “customer-first” mean to you?
👉 Ensuring the customer’s business impact is minimized, not just solving the technical issue.

---

# 🔢 **Aptitude / Logical Reasoning (10 Qs with Answers)**

**Q1.** If a server processes 240 requests in 4 minutes, how many in 1 minute?
✅ 60

**Q2.** Ratio of storage: HDD\:SSD = 4:1. If total is 500 TB, how much SSD?
✅ 100 TB

**Q3.** Probability of rolling an even number on a dice?
✅ 3/6 = 0.5

**Q4.** A log has 1200 entries, 25% are errors. How many error entries?
✅ 300

**Q5.** A server doubles its workload every 2 years. In 6 years, workload grows by?
✅ 2 × 2 × 2 = 8×

**Q6.** If latency reduces from 100ms to 25ms, % improvement?
✅ (100-25)/100 × 100 = 75%

**Q7.** A network packet drops 2% of the time. Out of 5000 packets, how many fail?
✅ 100

**Q8.** Server uptime is 99.9%. How much downtime per year?
✅ 0.1% of 365 days = \~8.76 hours

**Q9.** If disk read speed is 200 MB/s, how long to read 20 GB file?
✅ 20,000 MB ÷ 200 = 100 sec ≈ 1.67 min

**Q10.** If CPU utilization is 70% on 8 cores, how many cores fully busy?
✅ 8 × 0.7 = 5.6 ≈ 6 cores

---

# 🏢 **Company / Product Awareness (5 Qs)**

**Q1.** What does Nutanix do?
👉 Builds cloud software + hyperconverged infrastructure (HCI) for private/hybrid/multi-cloud.

**Q2.** What is Hyperconverged Infrastructure (HCI)?
👉 Software-defined architecture combining compute + storage + networking in one platform.

**Q3.** Name 3 Nutanix products.
👉 AOS (core HCI), AHV (hypervisor), Prism (management).

**Q4.** How is Nutanix different from traditional 3-tier infra (SAN/NAS)?
👉 Nutanix uses distributed storage fabric; eliminates separate SAN/NAS; scales like cloud.

**Q5.** What is Nutanix Prism?
👉 Unified management plane for clusters (monitoring, troubleshooting, automation).

---



