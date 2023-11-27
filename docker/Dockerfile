FROM openjdk:11-jre-slim-buster
RUN apt update && apt install -y jq && apt clean

COPY build/libs/ghco-intraday-pnl.jar /app.jar
COPY src/main/resources/ghco_trades.csv /ghco_trades.csv

ENTRYPOINT ["java","-jar","/app.jar","/ghco_trades.csv"]