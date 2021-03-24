# Engage Expense Service

The service for keeping data about customers' expenses.

## Architecture Overview

The intention of this architecture was to create loosely coupled module components that could be easily adjusted / replaced with a new ones.

### Hexagonal Architecture

Domain i.e. logic of the app is separated fromm its external dependencies by the means of ports and adapters. The idea here is to isolate the domain logic from the outside concerns. The biggest benefit that we could easily swap each component if necessary. The ports serve as the contract for the primary (incoming) adapters (i.e. **rest-api**) and secondary (outbound) adapters (i.e. **exchange-api**, **postgres**).

<p align="center">
  <img width="700" src="./docs/images/expense_architecture_overview.png">
</p>



