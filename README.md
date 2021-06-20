# Sign... Challenge

## Details
Our customer, a multinational company that builds industrial appliances, has an internal system dedicated to buy all resources he company requires to operate. Procurement is done via the company's own ERP.

A typical business process represented the ERP system is "procure-to-pay", involves the following activities:
* create purchase request
* request approved
* create purchase order
* select supplier
* receive goods
* pay invoice

Whenever the company wants to buy something, they do so through their ERP system.

Each resource purchase can be considered a case, or single instance of this process. The actual process often deviates from the ideal process. Sometimes purchase requests are raised but never get approved, sometimes a supplier is selected but the goods are never received, sometimes it simply takes a long time to complete the process, and so on. We call these deviations from the process path variants.

The customer provbides extracted process data for one of their processes for analysis: Procure to Pay, in a logfile that contains contain 3 columns:
* activity name
* case id
* timestamp

Analyse and compare process instances (cases) with each other using the sample data set [here](samples/Activity_Log_2004_to_2014.csv).

## Acceptance Criteria
* Aggregate cases that have the same event execution order and list the 10 variants with the most cases.
* As the interaction is interactive, we need to be able to get the query results in well under 50 milliseconds.
* Provide your output as JSON with a structure that makes sense.
