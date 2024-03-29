# RealTimeTradingSimulator-
Real-Time Trading simulator using live stock data from a Yahoo API, with User data and purchase history stored in a one-to-many CRUD Repository.
This project used a swing GUI as the front end as we have not been taught any front end for our Bootcamp. 
This was the final capstone project completed as a team during the last week of our 11 weeks JAVA Bootcamp.  

This was a 5-day project where we had to come up with a project and had 4 days to code the project from the ground up, as a result, 
there are many tasks that were not done or completed. 

![Trading-App](https://user-images.githubusercontent.com/79413585/224544865-68cbfce6-1720-437f-92e7-9cc26f51f7ba.png)


## My role in the team:

I was the backend engineer for this project using Java and Spring Boot to create all the entities and classes that connect to the H2 database.

## My task: 

As the backend engineer, I was in charge of creating the many layers needed to interact with the H2 database. 
The different layers include the controller which is the endpoint for HTTP request and response. 
The service layer contains the business logic and the repository layer which interacts with the H2 database. 
The entities that are stored in the database are the users and the stocks they own, this required further research into one-to-many relationships. 
Meaning that one user can own many stocks but one stock can only belong to one user. 
I also created the populator class to populate the initial stocks owned by the users and the user data e.g. username and password etc.

## Room for improvements:

Unfortunately, there wasn't enough time to complete and research the necessary HTTP requests that are needed to perform all the CRUD operations. 
As a result, only some of the get requests are functioning. 
So for the front-end engineers to access the backend, they do not call the HTTP request, instead, they interact with the backend directly. 

Also because of time constraints, a lot of testing was done manually instead of writing out test cases which would have been optimal. 

## Overall: 

Overall, I think having only had 4 days to research and code up the trading app, I feel like I've learnt a lot about Spring Boot and coding within a team. 
