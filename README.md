# CSSE Backend

## How to enable / disable email server

After cloning this repository, initialize local properties file as follows.

``` 
cd src/main/resources/
cp application-local-example.properties application-local.properties
```

To disable email functionality, update the main `application.properties` file as follows.

```
app.email.enabled=false
```

To enable email functionality, 

(i) Update the main `application.properties` file as follows.

```
app.email.enabled=true
```

(ii) Update the `application-local.properties` file with valid values. To obtain a new app password, 
log into your Google Account > go to Settings > Security > search `App Passwords` in top search bar > 
follow steps to create a new App Password. 
If `app.mode=test`, the system will send email to the configured email (`app.test.email`) here
(e.g. For doctor creation, the welcome email will be sent to `your-email@gmail.com` email).
If `app.mode=production`, the system will send email to the specified user's email
(e.g. For doctor creation, the welcome email will be sent to doctor's email).

``` 
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-email-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.from=your-email@gmail.com
app.mode=test
app.test.email=your-email@gmail.com
```
