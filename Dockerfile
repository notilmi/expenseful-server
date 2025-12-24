FROM amazoncorretto:17
MAINTAINER org.ilmi

COPY target/expenseful-server-0.0.1-SNAPSHOT.jar expenseful-server-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/expenseful-server-0.0.1-SNAPSHOT.jar"]