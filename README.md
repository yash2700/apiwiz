# apiwiz

Assignment 1:
MyClass.java contains assignment 1 which is solved with binary search.

Assignment 2:

apiwiz folder contains assignment 2.

Controllers for registration and login--RegistrationLoginController
<br>
1).
 user registration workflow with user schema provided below
    - first_name : string
    - last_name : string
    - email : string
    - phone_number : string
    - password : string
    
2).Users can login with their credentials using rest api endpoint in the controller RegistrationLoginController (/register/login)

Also user needs to authenticate with the same credentials for spring security using Basic Authentication.

<br>
Here session id is generated and is printed ont he console and also the credentials are saved for other endpoints. so that he needs not to be logged in again. and email is stored in HttpSession.
<br>
Controllers for 3 and 4 questions-

<br>
<br>
3).users can  subscribe to stock tickers with schema provided below.
    - stock_symbol:string ( ex:IBM).
    - notification_frequency:string (enum-Daily,Weekly,Biweekly,Monthly).
    - notify_time : string (17:13 UTC).
    
    Based on the email in HttpSession a Document is created with the details(Subscribe).
    
And using scheduling and cronjob the emails are sent on timely basis.

4).users can view stock details for the below given inputs
    - start_date : string
    - stock_symbol : string
    - end_date : string
    
    Here the api used is from the twelve data.
    

    
    




