### Project structure
 - common - beans and interfaces to communicate between modules
 - core - implementation of service layer to add transactions and to receive 
statistic
 - web - implementation of web layer
 - app - an spring boot application

### O(1) algorithm for both requests
We have some time window, which we want to analyse. The precision, as specified 
for timestamp field of transaction, is 1ms. So our period contains fixed count 
of items. For each item we can have its own statistic.

The count of 1ms's intervals inside time window is a _window size_

To add new transaction we just add it into appropriate statistic. To determine 
in which item we should add this transaction, we use:
> \<timestamp of transaction> % \<window size>

To receive statistics we just summarize all items. It's a loop with iteration count:
> \<window size>

Both operations are independent of the count of transactions.

Implementation class describes implementation details: 
> ru.gowk.n26.core.repository.RoundRobbinStatisticRepository

### Questions
As this is not specified in SRS and there was no answer from your side, I 
assume:
##### 1. "transactions" request - is amount<0 an allowable value?
In terms of bank transactions we should allow both (debit and credit).

##### 2. "transactions" request - could be the timestamp in the future?
It's a normal situation, because server and clients often have different times.

For such transactions we schedule a task to add them later, when the timestamp 
of transaction will be less then current time of server. 

##### 3. "transactions" request - can transactions be repeated?
We can have two transactions with the same values of amount and timestamp.

Information about amount and timestamp in real life isn't enough to have a 
guarantee that this is the same transaction. Especially when we don't know the 
source of this transactions.   

##### 4. When we respond with 201 - what does this means?
This means we received transaction, validated it and we have a guarantee that 
this transaction is useful in current or future statistic. 
