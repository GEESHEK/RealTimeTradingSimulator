# RealTimeTradingSimulator-
Real-Time Trading simulator using live stock data from a yahoo API, with User data and purchase history stored in a one-to-many CRUD Repository.
This project used a swing GUI as the front end as we have not been taught any front end for our bootcamp. 
This was the final capstone project completed as a team during the 11 weeks bootcamp.  

This was a 5 days project where we had to come up with a project and had 4 days to code the project from the ground up, as a result, 
there are many task that were not done or completed. 


My role in the team:

I was the backend engineer for this project using spring boot to create all the entities and classes that connect to the H2 database.

My task: 

As the backend engineer, I was in charge of creating the many layers needed to interact with the H2 database. 
The different layers include the controller which are the endpoint for HTTP request and response. 
The service layer contains the business logic and the repository layer which interacts with the H2 database. 
The entities that are stored in the database are the users and the stocks they own, this required further research into one-to-many relationships. 
Meaning that one user can own many stocks but one stock can only belong to one user. 
I also created the populator class to populate the initial stocks owned by the users and the user data e.g. username and password etc.

Room for improvements:

Unfortunately, there wasn't enough time to complete and research the necessary HTTP request that are needed to perform all the CRUD operations. 
As a result only some of the get request are functioning. 
So for the front end engineers to access the backend, they do not call the HTTP request, instead they interact with the backend directly. 

Also because of time constraints, a lot of testing was done manually instead of writing out test cases which would have been optimal. 

Overall: 

Overall, I think having only had 4 days to research and code up the trading app, I feel like I've learnt a lot about spring boot and coding within a team. 
