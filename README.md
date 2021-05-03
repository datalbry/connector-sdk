# Alxndria Connector SDK

The Alxndria Connector SDK provides everything required to easily build Connector integrations.

## Getting Started
TBD

### Properties
The Connector SDK requires various configurations to be set while installing.

| Property | Required | Example| Default | Description |
| --- | --- | --- | --- | --- |
| `io.datalbry.connector.alxndria.datasource` | Yes | a736c224-aa4d-457b-ae7f-2bb9b890d17d | - | The Alxndria datasource to use for synchronization. There must always be a 1:1 relationship between Connector and Datasource. |
| `io.datalbry.connector.alxndria.uri` | Yes | alxndria.datalbry.io | - | The URI to the Alxndria instance. |
| `io.datalbry.connector.concurrency` | No | 2 | 1 | The Concurrency level of the Connector. 2 will result into having two parallel worker per connector instance. |
| `spring.datasource.url` | Yes | jdbc:postgresql://host:port/database | - | The Postgres database to store the traversal information in |
| `spring.datasource.username` | Yes | admin | - | The user to authenticate to the database with |
| `spring.datasource.password` | Yes | admin123 | - | The password to authenticate to the database with |
| `spring.rabbitmq.host` | Yes | 168.24.24.0 | - | The host of the RabbitMQ broker |
| `spring.rabbitmq.port` | Yes | 5672 | - | The port of the RabbitMQ broker |
| `spring.rabbitmq.username` | Yes | rabbit-user | - | The user to authenticate with to the RabbitMQ broker with |
| `spring.rabbitmq.password` | Yes | secret | - | The password to authenticate with to the RabbitMQ broker with |

### Health Check
The Connector is bundled with `Spring Actuator` by default. 
This enables user to easily health check the Connector.

The health check can be accessed using the following endpoint:
> `GET <baseurl>:8080/actuator/health`


## License
>Copyright 2021 DataLbry.io
>
>Licensed under the Apache License, Version 2.0 (the "License");
>you may not use this file except in compliance with the License.
>You may obtain a copy of the License at
>
>http://www.apache.org/licenses/LICENSE-2.0
>
>Unless required by applicable law or agreed to in writing, software
>distributed under the License is distributed on an "AS IS" BASIS,
>WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
>See the License for the specific language governing permissions and
>limitations under the License.

