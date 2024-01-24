# Multi-Tenant Database Benchmark
## Project Overview

This project explores various strategies for storing multi-tenant data on different database management systems.

### What is Multi-Tenancy?
Multi-tenancy is a technique typically used in software as a service (SaaS) applications where a single database instance serves multiple customers, often referred to as tenants.
Each tenant's data is isolated and should be invisible  to other tenants.
The various strategies for storing multi-tenant data come with various performance tradeoffs that are explored below.

### What about Sharding?
Database sharding is a scaling technique that involves splitting data across multiple databases.
This benchmark is concerned with single node performance only at this time.
If a proper multi-tenant strategy is selected, modern database systems should be able to scale to a large number of tenants on a single server.

However, these various multi-tenant strategies can (and should be) used alongside sharding once the data has outgrown a single server.
Sharding handles splitting subsets of data across servers. Multi-Tenancy strategies are concerned with how to store the data within each database server.

Sharding without multi-tenancy is essentially database-per-customer and doesn't scale for many business models.

### Benchmarking Strategies:

* **Normalized Domain Model**:
Used as a control. 
All tenant data remains in tables with all other tenant data.
Looking up related data may require expensive subqueries or joins to ensure proper data isolation.
This is the typical data model companies follow until they start to hit scaling limitations.

* **Denormalized Tenant ID with simple keys/indexes**:
Every table gets a denormalized `tenant_id` column.
Primary keys, foreign keys are defined on `(id)`.
Every table always has at least one secondary index on `(tenant_id)`.

* **Denormalized Tenant ID with composite keys/indexes**:
Every table gets a denormalized `tenant_id` column.
Primary keys, foreign keys are defined on `(tenant_id, id)`.
Other indexes always start with the `tenant_id` column.

* **Hash-Based Partitioning**:
Multiple partitions of each table are created.
Tenant data is distributed across these tables based on a hash of the `tenant_id` column.

* **List-Based Partitioning**:
A partition of each table is created for each tenant based on its `tenant_id` column.

* **Namespace (Schema)-Based Isolation**:
Separate namespaces (schemas) are created per tenant.
Each tenant gets their own copy of each table, typically with its `tenant_id` as a suffix.

* **Citus Distributed Tables**:
Uses Citus's create_distributed_table on a [single database node](https://www.citusdata.com/blog/2021/03/20/sharding-postgres-on-a-single-citus-node/). 

### Tested Databases:
* Citus 12.1 (PostgreSQL 16.1)
* PostgreSQL 16.1
* MySQL 8.2
* MariaDB 11.3

## Getting Started
### Prerequisites
TODO: Java

[Docker](https://docs.docker.com/engine/install/) is required to actually run the benchmarks.
See the [Running the Benchmarks](#running-the-benchmarks) section if you'd like to generate `.sql` files to run against your own database instances.
In that case, docker isn't required and no containers will be started.

### Building
TODO

### Running the Benchmarks
TODO

### Results and Analysis
TBD

### Contributing
Please open a GitHub issue if you have any questions or suggestions.

### Acknowledgments
* [Shopify](https://www.shopify.com) and their [API](https://shopify.dev/docs/api) was used as an inspiration for the data model used in the benchmark.
