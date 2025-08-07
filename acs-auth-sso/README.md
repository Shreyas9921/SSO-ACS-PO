# ACS-Master-Service #
> Application with log4j2 rotation and Swagger-UI 2

- JBoss
- Auth 2.0
- Cache Management using H2 Database
- Wild Fly
- Key Clock
- Kafka Consumer

[Swagger-UI](http://localhost:9000/xpdel/api/v1/swagger-ui.html "Swagger URL")
[Embeddable-Demo](https://www.callicoder.com/hibernate-spring-boot-jpa-embeddable-demo/ "Embeddable Demo")
[Change Banner](http://patorjk.com/software/taag/#p=display&f=Graffiti&t=Type%20Something%20 "Change Banner")

## Versions ##
> Mongo DB Version _v3.2.22_
> MySql Version 14.14 Distrib 5.7.25

```cd .. && mvn clean install -DskipTests && cd ezr-oms-web-service && mvn spring-boot:run -Drun.arguments="--spring.profiles.active=prod" -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5090"```

## Steps to SetUp Server ##
1. Download Tomcat 9.0.16
2. Copy application.properies to Tomcat-9.0.16/conf/application.properties
3. Copy mail-templates at Tomcat-9.0.16/bin/mail-templates
3. Create Environment file - Tomcat-9.0.16/bin/setenv.sh
4. Write below in setenv.sh
Spring 1: JAVA_OPTS='-Dspring.profiles.active=prod -Dspring.config.location=<Tomcat-9.0.16 PATH>/conf/ -Duser.timezone=US/Pacific -Dlog4j.rootAppender="console" -Drun.jvmArguments="-Xms1g -Xmx2g -Xmn512m -Xss256m"'
Spring 2: JAVA_OPTS='-Dspring.profiles.active=prod -Dspring.config.additional-location=<Tomcat-9.0.16 PATH>/conf/ -Duser.timezone=US/Pacific -Dlog4j.rootAppender="console"  -Drun.jvmArguments="-Xms1g -Xmx2g -Xmn512m -Xss256m -XX:-HeapDumpOnOutOfMemoryError"'

JAVA_OPTS="$JAVA_OPTS -Xms512m -Xmx1024m -XX:MaxPermSize=512m -XX:+UseConcMarkSweepGC -XX:-HeapDumpOnOutOfMemoryError"
CATALINA_PID="<Tomcat-9.0.16 PATH>/temp/tomcat.pid"

5. See Log4J2 Files Path <Tomcat-9.0.16 PATH>/bin/logs
6. Edit <Tomcat-9.0.16 PATH>/conf/catalina.properties & Edit Array of tomcat.util.scan.StandardJarScanFilter.jarsToSkip & CommentOut annotations-api.jar, mail*.jar
7. Clean webapps dir and Copy War File into webapps/ROOT.war
export LOGGING_CONFIG="-Djava.util.logging.config.file=/opt/apache-tomcat-9.0.16/conf/log4j2.xml"
8. ./bin/catalina stop && ./bin/catalina start
9.  tail -f catalina.out | grep "ApiRequest"
10. tail -100000f catalina.out | egrep -i '/login|"password"' > out.log
11. Copy MySqlConnector.jar into Tomcat Lib

## Security Setup on MongoDB ##
> https://www.digitalocean.com/community/tutorials/how-to-install-and-secure-mongodb-on-ubuntu-16-04
```
use admin
db.createUser({user: "EZRAdmin", pwd: "EZRAdmin@123!", roles: [ { role: "root", db: "admin" } ]})

# Enabling Authentication
sudo nano /etc/mongod.conf

## Log Rotation ##
>https://jfrog.com/knowledge-base/how-to-configure-a-log-rotation-for-the-tomcats-catalina-out-log-file/
/opt/apache-tomcat-9.0.16/logs/catalina*.* {
        su root root
        notifempty
        copytruncate
        daily
        rotate 7
        compress
        missingok
        size 500M
}

vi /etc/logrotate.d/tomcat
/opt/apache-tomcat-9.0.16/logs/catalina.out {
    copytruncate
    daily
    rotate 60
    compress
    missingok
    size 10M
}

...
security:
  authorization: "enabled"
...
```

### AWS S3 Setup ###
1. Create IAM User and Copy _Access Key_ And _Secret_ And _ARN_ - Grant S3 Full Control
2. Create S3 Bucket And UN-CHECK _Block all public access_.
3. Set Bucket Policy as Below and **Replace** Principle AWS IAM ARN and **Replace** Resource Bucket Name
4. Setup CDN for more security and hide the bucket and region name
```
{
    "Version": "2012-10-17",
    "Id": "Policy1585848741552",
    "Statement": [
        {
            "Sid": "AllowArnPutObject",
            "Effect": "Allow",
            "Principal": {
                "AWS": "arn:aws:iam::388134916590:user/TestS3"
            },
            "Action": [
                "s3:GetObject",
                "s3:PutObject",
                "s3:PutObjectAcl",
                "s3:PutObjectTagging"
            ],
            "Resource": "arn:aws:s3:::avd-test/*"
        }
    ]
}
```

### Free Memory ###
```htop```
```ps aux | grep jar```

### Windows Create SSH Tunnel to Connect DB ###
>ssh -f ec2-user@cocktailkingdom.acs.master.net -L localhost:3306:localhost:9100 -N -i C:\Users\Advatix\Documents\Downloads\cocktailkingdom.pem

### MySQL PARTITIONING ###
```
ALTER TABLE wh_packaging_box
    PARTITION BY KEY(STATUS)
    PARTITIONS 3;


ALTER TABLE wh_packaging_box TRUNCATE PARTITION p1;

ALTER TABLE wh_packaging_box REMOVE PARTITIONING;
```

### Set Content Type in Request to Avoid Special Characters ###
`application/json;charset=utf-8`

### Create Tomcat Service ###
1. Create service files in /etc/systemd/system/ with the name of tomcat-fep.service.
2. Write below script in tomcat-fep.service
```[Unit]
Description=FEP Tomcat 9.0 servlet container
After=network.target
[Service]
Type=forking
User=ec2-user
Group=ec2-user
Environment="JAVA_HOME=/usr/java/jdk1.8.0_333-amd64/"
Environment="JAVA_OPTS=-Djava.security.egd=file:///dev/urandom"
Environment="CATALINA_BASE=/opt/apache-tomcat-fep/"
Environment="CATALINA_HOME=/opt/apache-tomcat-fep/"
Environment="CATALINA_PID=/opt/apache-tomcat-fep/temp/tomcat.pid"
Environment="CATALINA_OPTS=-Xms1024M -Xmx2048M -server -XX:+UseParallelGC"
ExecStart=/opt/apache-tomcat-fep/bin/startup.sh
ExecStop=/opt/apache-tomcat-fep/bin/shutdown.sh
[Install]
WantedBy=multi-user.target
```
3. Execute `chmod -x tomcat-fep.service`.
4. Execute `sudo systemctl daemon-reload`.
5. Execute `sudo service tomcat-fep start`.

### Capture TomCat Logs From Process Id ###
```jstack 5794 > jstak.txt```