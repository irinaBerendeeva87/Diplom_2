🚀**API Stellar.Burgers**🚀

As part of the training, I tested the API of the  Stellar Burgers training service using the JUnit 4, RestAssured and Allure frameworks.
The project uses Java 11 and used the Maven build system. And the Allure report was generated.
 
📝**Written tests**📝
- create a user;
- login a user;
- change user data ( with authorization and without authorization);
- create order;
- get orders from a specific user. 

📁**Documentation**📁 

You can find the documentation at https://code.s3.yandex.net/qa-automation-engineer/java/cheatsheets/paid-track/diplom/api-documentation.pdf

🔖**Instructions how to run:**🔖

1. Fork this repo and clone your version of the report. 
2. Run AllTest.java
3. Generate a report via command 
***allure serve target/surefire-reports/***

