# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

FROM openjdk:8-jdk AS BUILD

COPY sentinel-dashboard/ /source/sentinel/sentinel-dashboard/
WORKDIR /source/sentinel/sentinel-dashboard
RUN sh mvnw clean package -Dmaven.test.skip=true

FROM openjdk:8-jdk
LABEL maintainer="caodegao-jk@360jinrong.net"
COPY --from=BUILD /source/sentinel/sentinel-dashboard/target/sentinel-dashboard.jar /app.jar

ENTRYPOINT ["java","\-Dserver.port=18720","\-Dspring.profiles.active=${ENV}","-jar","/app.jar"]


# docker build -t sentinel-dashboard .
# docker run --rm -it --entrypoint /bin/bash sentinel-dashboard
# ./mvnw clean package -X -Dmaven.test.skip=true
