# syntax=docker/dockerfile:1
# ============================================================================
# config-server — imagen Docker. Servidor de configuración externalizada
# (Parte I: "propiedades de configuración externalizadas con un Config Server").
#
# En Docker se ejecuta con el perfil "native", sirviendo el repositorio de
# configuración montado en /config-repo (ver docker-compose.yml y la carpeta
# deploy/config-repo con los valores de la red Docker).
#
# El contexto de build es la RAÍZ del proyecto:
#   docker build -f config-server/Dockerfile -t config-server .
# ============================================================================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /workspace

COPY config-server/pom.xml config-server/pom.xml
COPY config-server/src config-server/src
RUN mvn -q -B -f config-server/pom.xml -DskipTests package

# ---------- Etapa de ejecución ----------
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /workspace/config-server/target/*.jar app.jar
EXPOSE 8888
HEALTHCHECK --interval=10s --timeout=5s --start-period=45s --retries=12 \
  CMD bash -c 'exec 3<>/dev/tcp/127.0.0.1/8888'
ENTRYPOINT ["java","-jar","/app/app.jar"]
