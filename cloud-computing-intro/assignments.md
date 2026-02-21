# Cloud Computing Introduction — Assignments

These assignments are designed to reinforce cloud concepts **without requiring an AWS account**. You will answer short-answer questions and create diagrams using [draw.io](https://app.diagrams.net/) (or another diagramming tool if you prefer).

To Submit your work, Create a new Git repository in your [Gitlab](https://gitlab.com/university-of-scranton/computing-sciences/courses/it-244/students) student sub-group, name the repository `cloud-intro-assignment`, select Visiblity Level Private, and select Initialize repository with a README, leave everything else unselected. For each of the assignments below create a folder. When completed your repository should look something like this:

```
cloud-intro-assignment/
├── README.md
├── Assignment 1/
│   └── cloud-1-answers.txt
├── Assignment 2/
│   └── cloud-2-answers.txt
├── Assignment 3/
│   ├── cloud-3-architecture.png
│   └── cloud-3-description.txt
└── Assignment 4/
    ├── cloud-4-answers.txt
    └── cloud-4-three-tier.png
```

---

## Assignment 1: Cloud concepts and providers

**Objective:** Explain basic cloud concepts and show awareness of multiple providers.

**Tasks:**

1. **Short answers** — In a text file named `cloud-1-answers.txt`, write 2–3 sentences for each of the following:
   - What is the main benefit of using IaaS (e.g., EC2, VPC) instead of buying your own servers?
   - Besides AWS, name two other major cloud providers and one place (e.g., documentation page) where someone could learn about their equivalent of “a virtual server” and “a virtual network.”

2. **Concept mapping** — In the same file, complete this sentence for each AWS service: “AWS [service name] is like [real-world or traditional IT equivalent] because …”
   - EC2  
   - VPC  
   - S3  
   - RDS  

**Submission:** `cloud-1-answers.txt`

---

## Assignment 2: Resource purpose and comparison

**Objective:** Describe what key cloud resources are and when you would use them.

**Tasks:**

1. **Purpose of resources** — In a text file named `cloud-2-answers.txt`, write one short paragraph (3–5 sentences) for each of the following. Focus on *what it is* and *what you use it for*:
   - **Security Group** (in the context of EC2)
   - **Subnet** (in the context of a VPC)
   - **IAM Role** (vs. storing a password on an EC2 instance)

2. **Choosing storage** — In the same file, answer in 2–3 sentences each:
   - You need a disk attached to a single EC2 instance for its operating system and application data. Which AWS storage type is appropriate, and why?
   - You need to store millions of image files that will be read by a web application. Which AWS storage type is appropriate, and why?

**Submission:** `cloud-2-answers.txt`

---

## Assignment 3: Architecture diagram (draw.io)

**Objective:** Draw a simple cloud architecture so that resources and relationships are clear. DrawIO has included elements for most AWS Services, use those to help you draw your diagram.

**Tasks:**

1. **Tool** — Use [draw.io](https://app.diagrams.net/) (diagrams.net). You can use the browser version; no account is required. If you use another tool (e.g., Lucidchart, Miro), that’s fine as long as the diagram is clear and exportable.

2. **Diagram** — Create a diagram that includes the following (use AWS names; you can add a short label in parentheses for the “real-world” equivalent if you like):
   - One **VPC** (draw it as a large box or boundary).
   - Inside the VPC:
     - One **public subnet** and one **private subnet** (label them).
     - One **EC2** instance in the public subnet (e.g., “Web server”).
     - One **EC2** instance in the private subnet (e.g., “App server”).
     - One **RDS** (database) in the private subnet.
   - Outside the VPC but connected in concept:
     - **Internet** (users) on one side.
     - **S3** (object storage) on the other side, with a note that the app might read/write objects here.
   - **Internet Gateway** attached to the VPC (so the public subnet can reach the internet).
   - Optional: a **Security Group** icon or note indicating that the web server allows HTTP/HTTPS from the internet and the app server allows traffic only from the web server.

3. **Export** — Export the diagram as a **PNG** or **PDF** and name it `cloud-3-architecture.png` or `cloud-3-architecture.pdf`.

4. **Short description** — In a text file named `cloud-3-description.txt`, write 3–5 sentences describing what your diagram shows: which parts are in the VPC, what is in each subnet, and how traffic might flow (e.g., user → web server → app server → database).

**Submission:** `cloud-3-architecture.png` and `cloud-3-description.txt`

---

## Assignment 4: Three-tier architecture and security

**Objective:** Relate a simple three-tier design to cloud resources and explain security boundaries. DrawIO has included elements for most AWS Services, use those to help you draw your diagram.

**Tasks:**

1. **Three-tier in the cloud** — In a text file named `cloud-4-answers.txt`, answer:
   - In a typical three-tier architecture (web tier, application tier, database tier), which tier(s) would you usually put in a **public subnet** and which in a **private subnet**? Why?
   - What is the purpose of a **Security Group** on the web tier (e.g., what traffic would you allow in and out)?
   - What is the purpose of a **Security Group** on the database tier (e.g., who should be allowed to connect)?

2. **Diagram** — Using [draw.io](https://app.diagrams.net/), draw a **simplified** three-tier diagram:
   - One box for “Internet / Users.”
   - One box for “Web tier” (e.g., EC2 or load balancer + EC2).
   - One box for “App tier” (EC2).
   - One box for “Database tier” (RDS).
   - Arrows showing allowed traffic flow (e.g., Users → Web → App → Database; no arrow from Internet directly to Database).
   - A short note or label indicating that the database is in a private subnet and only the app tier can reach it.

   Export as `cloud-4-three-tier.png`.

**Submission:** `cloud-4-answers.txt` and `cloud-4-three-tier.png`

---

## Quick reference

For background, use the [Cloud Computing Introduction README](README.md) and [Review Questions](review-questions.md).
