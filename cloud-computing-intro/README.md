# Cloud Computing Introduction

- [Review Questions](review-questions.md)
- [Assignments](assignments.md)

## What is Cloud Computing?

**Cloud computing** is the on-demand delivery of computing resources over the internet. Instead of buying and maintaining physical servers, storage, and networks in your own data center, you use services provided by a **cloud provider**. You pay for what you use (or reserve), and the provider handles the underlying hardware, power, cooling, and much of the maintenance.

### Why Use the Cloud?

- **Scalability**: Add or remove capacity as demand changes.
- **Cost**: Avoid large upfront purchases; pay for usage (or reserved capacity).
- **Reliability**: Providers offer multiple data centers (regions/availability zones) and managed services with high availability.
- **Speed**: Provision new servers, storage, or networks in minutes instead of ordering and installing hardware.

### Service Models (Brief Overview)

| Model | What you manage | What the provider manages | Example |
|-------|-----------------|---------------------------|---------|
| **IaaS** (Infrastructure as a Service) | Operating Systems, apps, data | Servers, storage, networking hardware | [EC2](https://docs.aws.amazon.com/ec2/), [VPC](https://docs.aws.amazon.com/vpc/), [S3](https://docs.aws.amazon.com/s3/) |
| **PaaS** (Platform as a Service) | Apps and data | Runtime, OS, servers | [AWS Elastic Beanstalk](https://docs.aws.amazon.com/elasticbeanstalk/), [RDS](https://docs.aws.amazon.com/rds/) |
| **SaaS** (Software as a Service) | Configuration and usage | Everything else | Gmail, Salesforce |

This course focuses mainly on **IaaS** concepts so you understand how cloud resources map to physical infrastructure.

---

## Cloud Providers: AWS and Others

This course uses **Amazon Web Services (AWS)** as the primary example because it is widely used in industry and has extensive documentation. **Other major cloud providers exist** and offer similar concepts under different names. Once you understand one provider’s model, the ideas transfer.

### Other Major Cloud Providers

| Provider | Main documentation / learning |
|----------|-------------------------------|
| **Microsoft Azure** | [Azure documentation](https://learn.microsoft.com/en-us/azure/) — [Azure for AWS users](https://learn.microsoft.com/en-us/azure/developer/java/fundamentals/azure-for-aws-developers) |
| **Google Cloud (GCP)** | [Google Cloud documentation](https://cloud.google.com/docs) — [GCP vs AWS mapping](https://cloud.google.com/docs/compare/aws) |
| **Oracle Cloud (OCI)** | [Oracle Cloud documentation](https://docs.oracle.com/en-us/iaas/Content/home.htm) |
| **IBM Cloud** | [IBM Cloud documentation](https://cloud.ibm.com/docs) |

When you read “EC2” or “VPC” below, think of the **concept** (virtual machine, virtual network); the same idea appears as **Azure Virtual Machines** and **Virtual Network**, **Google Compute Engine** and **VPC**, etc.

---

## Basic Cloud Resources: Concepts and Real-World Equivalents

The following sections describe common AWS resources and how they correspond to physical or traditional IT concepts. This gives you a mental model before you work with real labs or Infrastructure as Code later in the course.

### Compute

| AWS resource | What it is | Real-world equivalent |
|--------------|------------|------------------------|
| **[EC2](https://docs.aws.amazon.com/ec2/) (Elastic Compute Cloud)** | A virtual server in the cloud. You choose an OS (e.g., Amazon Linux, Ubuntu, Windows), CPU, memory, and storage. | A **virtual machine (VM)** or a physical server in a rack. Like a computer you can log into (SSH/RDP) and run software. |
| **[Lambda](https://docs.aws.amazon.com/lambda/)** | A function that runs in response to events (e.g., HTTP request, file upload). You provide code; AWS runs it without you managing a server. | A **serverless** compute unit — no VM to patch or scale; the provider runs your code when triggered. |

- **EC2** = “I need a machine to run my app.” You get a VM (instance) with an OS and resources you choose.
- **Lambda** = “I need to run this code when X happens.” No server to manage; you pay per invocation and duration.

### Networking

| AWS resource | What it is | Real-world equivalent |
|--------------|------------|------------------------|
| **[VPC](https://docs.aws.amazon.com/vpc/) (Virtual Private Cloud)** | An isolated network in the AWS cloud. Your EC2 instances, RDS databases, and other resources live inside a VPC. | Your **company network** or **LAN** — a private space where your machines can talk to each other and, if you allow it, to the internet. |
| **[Subnet](https://docs.aws.amazon.com/vpc/latest/userguide/configure-subnets.html)** | A segment of a VPC (e.g., a range of IP addresses). Often split into *public* (internet-facing) and *private* (no direct internet). | A **subnet** or **VLAN** in a physical network — a logical slice of the network (e.g., “building A,” “DMZ”). |
| **[Internet Gateway (IGW)](https://docs.aws.amazon.com/vpc/latest/userguide/VPC_Internet_Gateway.html)** | Attaches to a VPC and allows traffic between the VPC and the public internet. | The **router/firewall** that connects your internal network to the internet. |
| **[Security Group](https://docs.aws.amazon.com/vpc/latest/userguide/VPC_SecurityGroups.html)** | Virtual firewall for an EC2 instance (or other service). Rules control inbound and outbound traffic (by port, protocol, source/destination). | **Host firewall** or **instance-level firewall** — e.g., “allow SSH from this IP, allow HTTP from anywhere.” |
| **[Network ACL (NACL)](https://docs.aws.amazon.com/vpc/latest/userguide/vpc-network-acls.html)** | Optional firewall at the subnet level. Evaluates traffic in and out of the subnet. | **Subnet-level firewall** — an extra layer of rules at the network segment. |
| **[Route Table](https://docs.aws.amazon.com/vpc/latest/userguide/VPC_Route_Tables.html)** | Defines how traffic is routed inside the VPC (e.g., “send 0.0.0.0/0 to the Internet Gateway”). | **Routing table** on a router — “which path should this packet take?” |

- **VPC** = your private cloud network.
- **Subnets** = segments of that network (e.g., one per tier: web, app, database).
- **Security Groups** and **NACLs** = who can talk to whom and on which ports.

### Storage

| AWS resource | What it is | Real-world equivalent |
|--------------|------------|------------------------|
| **[S3](https://docs.aws.amazon.com/s3/) (Simple Storage Service)** | Object storage. You store *objects* (files) in *buckets*. Each object has a key (path), data, and metadata. Highly durable and scalable. | **File server** or **NAS** — place to put files (documents, images, backups). Not a “drive” you mount like a disk; you access via API or console. |
| **[EBS](https://docs.aws.amazon.com/ebs/) (Elastic Block Store)** | Block storage attached to an EC2 instance. Persists independently of the instance. | **Hard disk** or **SSD** attached to a server — a volume the OS sees as a disk (e.g., `/dev/sdf`). |
| **[EFS](https://docs.aws.amazon.com/efs/) (Elastic File System)** | Managed NFS-style file system that can be shared across multiple EC2 instances. | **Network file share (NFS)** or **shared drive** — multiple servers see the same files. |

- **S3** = store files/objects (backups, static assets, data lakes).
- **EBS** = disk for a single EC2 instance.
- **EFS** = shared files across many instances.

### Database

| AWS resource | What it is | Real-world equivalent |
|--------------|------------|------------------------|
| **[RDS](https://docs.aws.amazon.com/rds/) (Relational Database Service)** | Managed relational database (MySQL, PostgreSQL, MariaDB, SQL Server, Oracle). AWS handles patching, backups, and often high availability. | A **database server** (e.g., a server running MySQL) — but the provider runs and maintains the OS and database engine. |
| **[DynamoDB](https://docs.aws.amazon.com/dynamodb/)** | Managed NoSQL key-value and document database. Serverless; you don’t manage instances. | A **managed NoSQL database** — like running MongoDB or a key-value store, but fully managed. |

- **RDS** = “I need a relational database.” You get a DB endpoint; you don’t SSH into the server.
- **DynamoDB** = managed NoSQL for flexible schema and scale.

### Identity and Access

| AWS resource | What it is | Real-world equivalent |
|--------------|------------|------------------------|
| **[IAM](https://docs.aws.amazon.com/iam/) (Identity and Access Management)** | Users, groups, roles, and policies that define *who* can do *what* in your AWS account (e.g., “allow user X to start/stop EC2 in region Y”). | **User accounts and permissions** — like Active Directory or Unix users/groups, but for cloud APIs and resources. |
| **[IAM User](https://docs.aws.amazon.com/IAM/latest/UserGuide/id_users.html)** | Identity for a person or application with long-term credentials (access key + secret). | A **login account** for a person or a service account. |
| **[IAM Role](https://docs.aws.amazon.com/IAM/latest/UserGuide/id_roles.html)** | Identity that can be *assumed* (e.g., by an EC2 instance or Lambda). Temporary credentials; no long-term secret stored. | **Role-based access** — “this server or app has permission to do X” without embedding a password. |

- **IAM** = who can use AWS and what they’re allowed to do.

### Other Common Building Blocks

| AWS resource | What it is | Real-world equivalent |
|--------------|------------|------------------------|
| **[ELB / ALB / NLB](https://docs.aws.amazon.com/elasticloadbalancing/)** (Elastic Load Balancer / Application / Network Load Balancer) | Distributes incoming traffic across multiple targets (e.g., EC2 instances). | **Load balancer** — a single entry point that spreads load across several servers. |
| **[CloudFront](https://docs.aws.amazon.com/cloudfront/)** | Content delivery network (CDN). Caches content at edge locations close to users. | **CDN** — like Akamai or Cloudflare; faster delivery of static and some dynamic content. |
| **[Route 53](https://docs.aws.amazon.com/route53/)** | DNS service. Hosted zones, record sets, and health checks. | **DNS server** — translates domain names to IP addresses and can route traffic. |
| **[CloudWatch](https://docs.aws.amazon.com/cloudwatch/)** | Monitoring and observability: metrics, logs, alarms. | **Monitoring and logging** — like Nagios + centralized logs. |

---

## How It Fits Together (Conceptual)

A simple web application in the cloud might look like this at a high level:

1. **[VPC](https://docs.aws.amazon.com/vpc/)** — Your isolated network.
2. **Subnets** — e.g., public subnets for web servers, private subnets for app and database.
3. **[EC2](https://docs.aws.amazon.com/ec2/)** — Virtual machines running your web/app code.
4. **[Security Groups](https://docs.aws.amazon.com/vpc/latest/userguide/VPC_SecurityGroups.html)** — Allow HTTP/HTTPS from the internet to the web tier, and only allow the web tier to talk to the app/database tier.
5. **[ALB](https://docs.aws.amazon.com/elasticloadbalancing/)** — Sits in front of EC2 and distributes traffic.
6. **[RDS](https://docs.aws.amazon.com/rds/)** — Managed database in a private subnet.
7. **[S3](https://docs.aws.amazon.com/s3/)** — Static assets (images, CSS, JS) or backups.
8. **[IAM](https://docs.aws.amazon.com/iam/)** — Roles so EC2 can read from S3 or RDS without storing passwords.

For a short demo of hosting a web application on AWS and a walkthrough of the AWS console, see [this introductory video](https://www.youtube.com/watch?v=Nzv-tzU-UAw).

---

## Learning Without an AWS Account

You don’t need an AWS account for this introductory week. The [assignments](assignments.md) use **short-answer questions** and **diagrams** (e.g., with [draw.io](https://app.diagrams.net/)) so you can practice mapping concepts and architectures without logging in. Hands-on AWS work will come in later weeks when accounts are available.

---

## Additional Resources

### AWS (used in this course)

- [AWS Documentation](https://docs.aws.amazon.com/)
- [AWS Free Tier](https://aws.amazon.com/free/) — For when you do get an account
- [AWS Architecture Center](https://aws.amazon.com/architecture/) — Reference architectures and best practices

### Other providers (for comparison and further study)

- [Microsoft Azure documentation](https://learn.microsoft.com/en-us/azure/)
- [Google Cloud documentation](https://cloud.google.com/docs)
- [Oracle Cloud documentation](https://docs.oracle.com/en-us/iaas/Content/home.htm)

### AWS Certifications

AWS offers role-based certifications that validate cloud skills and are widely recognized by employers. A common path is to start with **AWS Certified Cloud Practitioner** (foundational), then move to **Solutions Architect Associate** or **Developer Associate** as you gain experience.

- [AWS Certification overview](https://aws.amazon.com/certification/) — All certifications, exam overview, and benefits
- [Explore certification exams](https://aws.amazon.com/certification/exams/) — Exam blueprints, sample questions, and preparation resources
- [AWS Skill Builder](https://skillbuilder.aws/) — Free digital training and learning plans (including exam prep)
- [AWS Training and Certification](https://aws.amazon.com/training/) — Official courses and certification policies
- [Certification policies](https://aws.amazon.com/certification/policies/) — Eligibility, pricing, recertification, and test-day rules

### General cloud concepts

- [NIST Definition of Cloud Computing](https://csrc.nist.gov/publications/detail/sp/800-145/final) — IaaS, PaaS, SaaS definitions
- [draw.io (diagrams.net)](https://app.diagrams.net/) — Free tool for drawing architecture diagrams (no account required in browser)
