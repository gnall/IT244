# Review Questions: Cloud Computing Introduction

Test your understanding with these questions. Try to answer before checking the solution.

### Cloud basics

1. **What is cloud computing, and how does it differ from running your own servers in a data center?**
   <details>
   <summary>Answer</summary>
   Cloud computing is the on-demand delivery of computing resources over the internet. You use services (compute, storage, network) provided by a cloud provider instead of buying and maintaining physical servers. You typically pay for what you use, and the provider handles hardware, power, cooling, and much of the maintenance.
   </details>

2. **What do IaaS, PaaS, and SaaS stand for? Who manages the OS in each?**
   <details>
   <summary>Answer</summary>
   **IaaS** (Infrastructure as a Service): You manage OS, apps, and data; the provider manages servers, storage, networking. **PaaS** (Platform as a Service): You manage apps and data; the provider manages runtime, OS, and infrastructure. **SaaS** (Software as a Service): You mainly configure and use the app; the provider manages everything else. In IaaS you manage the OS; in PaaS and SaaS the provider manages the OS.
   </details>

3. **Name at least two major cloud providers besides AWS. Where can you find their documentation?**
   <details>
   <summary>Answer</summary>
   Microsoft Azure, Google Cloud (GCP), Oracle Cloud (OCI), IBM Cloud. Documentation: Azure (learn.microsoft.com/azure), Google Cloud (cloud.google.com/docs), Oracle (docs.oracle.com/iaas), IBM (cloud.ibm.com/docs). See the [README](README.md) for direct links.
   </details>

### Compute and networking

4. **What real-world thing does an AWS EC2 instance correspond to?**
   <details>
   <summary>Answer</summary>
   A **virtual machine (VM)** or a physical server. It’s a virtual server in the cloud with an OS, CPU, memory, and storage that you can log into and run software on.
   </details>

5. **What is a VPC, and what physical or traditional concept is it like?**
   <details>
   <summary>Answer</summary>
   A **VPC (Virtual Private Cloud)** is an isolated network in the cloud where your resources (EC2, RDS, etc.) live. It’s like your **company network** or **LAN** — a private space for your machines to communicate and, if you allow it, reach the internet.
   </details>

6. **What is the difference between a Security Group and a Network ACL (NACL)?**
   <details>
   <summary>Answer</summary>
   A **Security Group** is a firewall at the **instance** level (e.g., attached to an EC2 instance); it controls inbound and outbound traffic for that instance. A **NACL** is a firewall at the **subnet** level; it evaluates traffic entering or leaving the subnet. Both can be used together for defense in depth.
   </details>

7. **What is a subnet, and why might you use public vs. private subnets?**
   <details>
   <summary>Answer</summary>
   A **subnet** is a segment of a VPC (a range of IP addresses). **Public subnets** typically have a route to the internet (via an Internet Gateway) and are used for resources that need direct internet access (e.g., load balancers, bastion hosts). **Private subnets** do not have a direct route to the internet and are used for application servers, databases, etc., to reduce exposure.
   </details>

### Storage and database

8. **What is S3, and what is it similar to in a traditional IT environment?**
   <details>
   <summary>Answer</summary>
   **S3 (Simple Storage Service)** is object storage: you store files (objects) in buckets and access them by key/path. It’s similar to a **file server** or **NAS** — a place to put files (documents, images, backups). You don’t mount it like a disk; you use the API or console.
   </details>

9. **What is the difference between EBS and S3?**
   <details>
   <summary>Answer</summary>
   **EBS** is **block storage** attached to a single EC2 instance — like a **hard disk** the OS uses for a filesystem. **S3** is **object storage** — you store and retrieve whole objects (files) in buckets; it’s not attached as a disk to a VM. EBS is for instance storage; S3 is for scalable file/object storage.
   </details>

10. **What does RDS provide, and what would you use it for?**
    <details>
    <summary>Answer</summary>
    **RDS (Relational Database Service)** is a **managed relational database** (e.g., MySQL, PostgreSQL). AWS runs the database engine and handles patching, backups, and often high availability. You’d use it when you need a relational database without managing the underlying server yourself.
    </details>

### Identity and other services

11. **What is IAM, and what real-world concept does it resemble?**
    <details>
    <summary>Answer</summary>
    **IAM (Identity and Access Management)** is AWS’s system for **users, groups, roles, and policies** — it defines who can do what in your AWS account. It’s like **user accounts and permissions** (e.g., Active Directory or Unix users/groups) but for cloud APIs and resources.
    </details>

12. **What is an Application Load Balancer (ALB) used for?**
    <details>
    <summary>Answer</summary>
    An **ALB** distributes incoming traffic across multiple targets (e.g., EC2 instances). It’s a **load balancer** — a single entry point (URL or IP) that spreads requests across several servers for availability and performance.
    </details>

13. **What is Lambda, and how is it different from EC2?**
    <details>
    <summary>Answer</summary>
    **Lambda** is **serverless** compute: you provide code that runs in response to events (e.g., HTTP, file upload); AWS runs it without you managing a server. **EC2** is a virtual machine you manage (OS, scaling, patching). With Lambda you don’t provision or manage instances; you pay per invocation and execution time.
    </details>
