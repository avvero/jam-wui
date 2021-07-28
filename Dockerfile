FROM adoptopenjdk:11-jre-hotspot

ADD build/libs/jam-wui.jar jam-wui.jar

CMD ["java", "-jar", "jam-wui.jar"]