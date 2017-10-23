# spring-boot-starter

[![Build Status](https://travis-ci.org/muxfe/spring-boot-starter.svg?branch=master)](https://travis-ci.org/muxfe/spring-boot-starter.svg?branch=master)

一个简单的 SpringBoot 开发基础框架，记录学习过程和踩的坑。

## REST API

- `GET /api/[repository]`
  - Accept: `application/json;chartset=utf-8`
  - Status: 200 Ok
  - QueryString
    - page(=0)
    - size(=20)
    - sort(`[property],asc|desc`)
  - Response
    - _embedded
      - `[entity]`(=[])
    - page
      - size
      - number
      - totalElements
      - totalPages
- `GET /api/[repository]/fuzzy`
  - Accept: `application/json;chartset=utf-8`
  - Status: 200 Ok
  - QueryString
    - page(=0)
    - size(=20)
    - sort(`[property],asc|desc`)
    - selective `[Entity]` properties(exclude `id`)
  - Response
    - _embedded
      - `[entity]`(=[])
    - page
      - size
      - number
      - totalElements
      - totalPages
- `POST /api/[repository]`
  - ContentType: `application/json`
  - Accept: `application/json;chartset=utf-8`
  - Status: 201 Created
  - Body
    - `[Entity]`
  - Response
    - new `[Entity]`
- `PUT /api/[repository]/[id]`
  - ContentType: `application/json`
  - Accept: `application/json;chartset=utf-8`
  - Status: 200 Ok
  - Body
    - **All** `[Entity]` properties
  - Response
    - updated `[Entity]`
- `PATCH /api/[repository]/[id]`
  - ContentType: `application/json`
  - Accept: `application/json;chartset=utf-8`
  - Status: 200 Ok
  - Body
    - **Partial** `[Entity]` properties
  - Response
    - updated `[Entity]`
- `DELETE /api/[repository]/[id]`
  - Status 204 NoContent

## TODO

- [X] SpringBootApplication
- [X] Jpa Crud Operation
- [X] Entity and Controller Examples
- [X] Unit Test
- [X] `ExposeIdsRepositoryRestConfiguration`
- [X] Support CORS
- [X] Fuzzy search
- [X] Application initializer
- [ ] Entity Projection

## References

### Spring Boot

- <https://spring.io/guides/gs/spring-boot/>
- <https://spring.io/guides/gs/accessing-data-rest/>
- <https://spring.io/guides/topicals/spring-security-architecture/>
- <https://spring.io/guides/tutorials/react-and-spring-data-rest/>
- <https://docs.spring.io/spring-boot/docs/1.5.7.RELEASE/reference/html/>
- <https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html/>