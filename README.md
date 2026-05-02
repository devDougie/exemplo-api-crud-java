# 📚 Java CRUD — Projeto de Referência

Projeto backend de referência para consulta em desenvolvimentos futuros. Implementa um CRUD completo de pessoas utilizando Java 17, Spring Boot 3.5.14, JDBC e MySQL, seguindo a arquitetura REST e os padrões de organização de código utilizados no mercado.

---

## 🛠️ Tecnologias utilizadas

### Java 17
Linguagem principal do projeto. A versão 17 é uma versão LTS (Long-Term Support), ou seja, recebe suporte e atualizações por um longo período — por isso é a escolha padrão do mercado para novos projetos.

### Maven
Gerenciador de dependências e build do projeto. É responsável por baixar as bibliotecas necessárias, compilar o código e empacotar a aplicação. Todas as dependências ficam declaradas no arquivo `pom.xml`. O projeto utiliza o `spring-boot-starter-parent` como parent POM, o que gerencia automaticamente as versões compatíveis das dependências Spring.

### Spring Boot 3.5.14
Framework que simplifica a criação de aplicações Java. Ele configura automaticamente grande parte do projeto (banco de dados, servidor web, serialização JSON), eliminando a necessidade de configurações manuais extensas. O Spring Boot embute um servidor Tomcat, ou seja, não é necessário instalar nenhum servidor externo — a aplicação sobe com um simples `main`.

### Spring Web (MVC)
Módulo do Spring responsável por expor os endpoints HTTP da aplicação. Habilita anotações como `@RestController`, `@GetMapping`, `@PostMapping`, `@PutMapping` e `@DeleteMapping`. Utiliza o padrão MVC internamente, mas como é uma API REST, não há camada de View — apenas dados JSON são retornados.

### Spring Data JDBC
Módulo do Spring para acesso ao banco de dados via JDBC puro. Diferente do JPA/Hibernate (que mapeia objetos automaticamente para tabelas), o Spring Data JDBC com `JdbcTemplate` exige que as queries SQL sejam escritas manualmente — o que é ideal para aprendizado, pois torna o acesso ao banco explícito e transparente. O `JdbcTemplate` também gerencia automaticamente a abertura e o fechamento de conexões.

<!-- ### HikariCP
Pool de conexões incluído automaticamente pelo Spring Boot. Em vez de abrir uma nova conexão com o banco a cada requisição (o que é custoso), o HikariCP mantém um conjunto de conexões reutilizáveis. No console de inicialização ele aparece como `HikariPool-1 - Start completed`. -->

### MySQL
Sistema de gerenciamento de banco de dados relacional. Roda como um serviço independente e aceita conexões via rede — por isso a URL de conexão usa `localhost:3306` e não um caminho de arquivo Windows. O driver de conexão utilizado é o `mysql-connector-j`.

### Bean Validation (Hibernate Validator)
Biblioteca que implementa a especificação Bean Validation do Java. Habilita anotações como `@NotBlank`, `@Email`, `@Pattern`, `@DecimalMin` e `@DecimalMax` no `PersonRequestDTO` para validar automaticamente os dados de entrada antes de chegarem à lógica de negócio. Apesar do nome "Hibernate Validator", não tem nenhuma relação com o Hibernate ORM — são projetos independentes que compartilham apenas o nome da organização.

### SpringDoc OpenAPI 2.8.16 (Swagger UI)
Biblioteca adicionada manualmente no `pom.xml` (não disponível no Spring Initializr). Gera automaticamente a documentação da API e disponibiliza uma interface visual interativa para testar os endpoints sem precisar de Postman ou Insomnia. Acessível em `http://localhost:8080/swagger-ui.html` enquanto a aplicação estiver rodando. Os endpoints são documentados com `@Tag` e `@Operation`.

---

## 📁 Estrutura do projeto

```
crud/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/api/crud/
│   │   │       ├── CrudApplication.java            ← Ponto de entrada da aplicação
│   │   │       ├── controller/
│   │   │       │   └── PersonController.java       ← Recebe e responde requisições HTTP
│   │   │       ├── service/
│   │   │       │   └── PersonService.java          ← Regras de negócio + conversão DTO ↔ Model
│   │   │       ├── repository/
│   │   │       │   └── PersonRepository.java       ← Acesso ao banco via JdbcTemplate
│   │   │       ├── model/
│   │   │       │   └── Person.java                 ← Entidade de domínio (inclui enum Gender)
│   │   │       ├── dto/
│   │   │       │   ├── PersonRequestDTO.java       ← Dados de entrada (com validações)
│   │   │       │   └── PersonResponseDTO.java      ← Dados de saída (imutável, sem setters)
│   │   │       └── exception/
│   │   │           ├── PersonNotFoundException.java
│   │   │           └── GlobalExceptionHandler.java ← Tratamento centralizado de erros
│   │   └── resources/
│   │       ├── application.properties              ← Configurações da aplicação
│   │       ├── static/                             ← Arquivos estáticos (gerado pelo Initializr)
│   │       └── templates/                          ← Templates de view (gerado pelo Initializr)
│   └── test/
│       └── java/
│           └── com/api/crud/
│               └── CrudApplicationTests.java       ← Teste básico de contexto
├── .mvn/wrapper/
│   └── maven-wrapper.properties                    ← Configuração do Maven Wrapper
├── .idea/                                          ← Configurações do IntelliJ (não versionar)
├── target/                                         ← Arquivos compilados (não versionar)
├── .gitattributes                                  ← Configurações de linha do Git
├── .gitignore                                      ← Arquivos ignorados pelo Git
├── HELP.md                                         ← Links de documentação (gerado pelo Initializr)
├── mvnw                                            ← Maven Wrapper para Linux/Mac
├── mvnw.cmd                                        ← Maven Wrapper para Windows
└── pom.xml                                         ← Dependências e configurações Maven
```

### Por que essa separação em camadas?

O padrão **Controller → Service → Repository** (arquitetura em camadas) é o mais adotado no mercado:

- **Controller** não acessa o banco diretamente — apenas recebe HTTP, delega ao Service e devolve a resposta
- **Service** concentra as regras de negócio e a conversão entre DTOs e Model
- **Repository** é o único que fala com o banco — se o banco mudar, só ele precisa ser alterado
- Cada camada tem uma responsabilidade única, o que facilita manutenção e testes

---

## 📂 Arquivos e diretórios gerados pelo Spring Initializr

As configurações abaixo foram utilizadas para gerar a base do projeto em [start.spring.io](https://start.spring.io):

![Spring Initializr](https://github.com/user-attachments/assets/2589d813-408e-4ba1-8f4c-fab4c746cf40)

### `pom.xml`
Arquivo de configuração do Maven. Define as dependências do projeto, a versão do Java (`17`), o `groupId` (`com.api`), o `artifactId` (`crud`) e os plugins de build. O `spring-boot-starter-parent` como parent POM garante que todas as dependências Spring usem versões compatíveis entre si, sem necessidade de declarar versões manualmente.

### `src/main/java/com/api/crud/CrudApplication.java`
Classe principal da aplicação. Contém o método `main` que inicializa o Spring Boot via `SpringApplication.run`. A anotação `@SpringBootApplication` é um atalho que combina três anotações: `@Configuration` (classe de configuração), `@EnableAutoConfiguration` (configuração automática) e `@ComponentScan` (escaneia os pacotes em busca de componentes como `@Service`, `@Repository` e `@RestController`). Não é necessário modificá-la.

### `src/main/resources/application.properties`
Arquivo central de configurações da aplicação. Contém as credenciais do banco de dados, a configuração do Swagger e o nível de log. O Spring Boot lê esse arquivo automaticamente na inicialização. Nunca commite senhas reais — em projetos reais, use variáveis de ambiente.

### `src/main/resources/static/`
Pasta para arquivos estáticos como HTML, CSS e JavaScript que seriam servidos diretamente pelo servidor. Não é utilizada neste projeto pois é uma API REST pura, sem interface web.

### `src/main/resources/templates/`
Pasta para templates de view (Thymeleaf, FreeMarker, etc.) que seriam renderizados pelo servidor. Não é utilizada neste projeto pois é uma API REST — os dados são retornados como JSON, não como páginas HTML.

### `src/test/java/com/api/crud/CrudApplicationTests.java`
Classe de teste gerada automaticamente. Contém um teste básico (`contextLoads`) que verifica se o contexto do Spring sobe sem erros. É o ponto de partida para os testes da aplicação.

### `.mvn/wrapper/maven-wrapper.properties`
Configuração do Maven Wrapper. Define qual versão do Maven será usada ao executar `mvnw`. Permite que qualquer pessoa rode o projeto sem precisar ter o Maven instalado na máquina.

### `mvnw` e `mvnw.cmd`
Scripts do Maven Wrapper. `mvnw` é para Linux/Mac e `mvnw.cmd` para Windows. Permitem buildar e rodar o projeto via terminal:
```bash
./mvnw spring-boot:run   # Linux/Mac
mvnw.cmd spring-boot:run # Windows
```

### `.gitignore`
Instrui o Git a ignorar arquivos que não devem ser versionados: pasta `target/` (arquivos compilados), arquivos `.class`, configurações locais da IDE e afins.

### `.gitattributes`
Define como o Git deve tratar certos arquivos — por exemplo, normalização de quebras de linha entre Windows (`CRLF`) e Linux/Mac (`LF`).

### `HELP.md`
Arquivo gerado pelo Initializr com links para a documentação oficial do Spring Boot e das dependências escolhidas. Pode ser deletado sem impacto no projeto.

### `.idea/`
Pasta gerada pelo IntelliJ com configurações locais da IDE. Não deve ser versionada — já está no `.gitignore`.

### `target/`
Pasta gerada pelo Maven com os arquivos compilados (`.class`), o JAR final e outros artefatos de build. Não deve ser versionada — já está no `.gitignore`.

---

## 📋 Descrição dos arquivos Java

### `model/Person.java`
Representa a entidade de domínio do projeto. Contém os campos que espelham as colunas da tabela `persons` no banco de dados. O enum `Gender` está definido como classe interna pois só faz sentido no contexto de `Person`. Usa `BigDecimal` para `height` e `weight` para garantir precisão decimal exata — `float` e `double` introduzem erros de arredondamento de ponto flutuante.

### `dto/PersonRequestDTO.java`
Representa os dados que chegam nas requisições `POST` e `PUT`. Contém as anotações de validação ativadas pelo `@Valid` no Controller. Separa a responsabilidade de validação do modelo de domínio.

Validações aplicadas:
- `fullName`: obrigatório (`@NotBlank`), máximo 150 caracteres (`@Size`)
- `email`: obrigatório (`@NotBlank`), formato de e-mail válido (`@Email`)
- `phone`: opcional, aceita apenas dígitos e `+` no início, entre 7 e 20 caracteres (`@Pattern`)
- `height`: entre `0.50` e `2.72` metros (`@DecimalMin` / `@DecimalMax`)
- `weight`: entre `2.0` e `500.0` kg (`@DecimalMin` / `@DecimalMax`)

### `dto/PersonResponseDTO.java`
Representa os dados retornados nas respostas da API. É **imutável** — possui apenas construtor com todos os campos e getters, sem setters. Isso garante que o objeto de resposta não seja modificado após ser criado. Também controla exatamente o que é exposto ao cliente.

### `exception/PersonNotFoundException.java`
Exceção de negócio lançada quando uma pessoa não é encontrada pelo ID. Estende `RuntimeException` (unchecked), o que evita a necessidade de `try/catch` em todos os lugares que a lançam. Mensagem padronizada: `"Pessoa com ID {id} não encontrada."`.

### `exception/GlobalExceptionHandler.java`
Intercepta exceções lançadas em qualquer Controller e retorna respostas JSON padronizadas. A anotação `@RestControllerAdvice` garante que os retornos sejam sempre JSON. Trata dois tipos de erro:
- `PersonNotFoundException` → `404 Not Found` com `{"error": "mensagem"}`
- `MethodArgumentNotValidException` → `400 Bad Request` com mapa de campos e mensagens de validação

### `repository/PersonRepository.java`
Única camada que acessa o banco de dados. Utiliza `JdbcTemplate` injetado via construtor. O `RowMapper` é um lambda que converte cada linha do `ResultSet` em um objeto `Person`. Todos os métodos usam `?` como placeholder — proteção automática contra SQL Injection via `PreparedStatement`.

Métodos:
- `findAll()` → `SELECT * FROM persons`
- `findById(Long id)` → `SELECT * FROM persons WHERE id = ?`
- `save(Person p)` → `INSERT INTO persons (...) VALUES (?, ?, ?, ?, ?, ?)`
- `update(Long id, Person p)` → `UPDATE persons SET ... WHERE id = ?`
- `delete(Long id)` → `DELETE FROM persons WHERE id = ?`

### `service/PersonService.java`
Camada de negócio da aplicação. Converte `PersonRequestDTO` → `Person` via `toModel`, chama o Repository e converte o resultado de volta via `toResponseDTO`. Também lança `PersonNotFoundException` quando um registro não existe — tanto no `findById` quanto antes do `update` e `delete`.

### `controller/PersonController.java`
Camada HTTP da aplicação. Expõe cinco endpoints REST no caminho base `/api/persons`. Usa `@Valid` para acionar as validações do `PersonRequestDTO`. Retorna `ResponseEntity` com os status HTTP corretos. As anotações `@Tag` e `@Operation` são exclusivas do Swagger e não afetam o funcionamento da API.

---

## 🗃️ Banco de dados

### Script de criação

```sql
CREATE DATABASE IF NOT EXISTS crud_db;

USE crud_db;

CREATE TABLE IF NOT EXISTS persons (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(150)  NOT NULL,
    email     VARCHAR(255)  NOT NULL UNIQUE,
    phone     VARCHAR(20),
    gender    ENUM('MALE', 'FEMALE', 'OTHER'),
    height    DECIMAL(3,2),
    weight    DECIMAL(5,1)
);
```

### Tipos de dados

| Campo Java | Tipo Java | Tipo SQL | Motivo |
|---|---|---|---|
| `fullName` | `String` | `VARCHAR(150)` | Texto com limite definido |
| `email` | `String` | `VARCHAR(255)` | Padrão de tamanho para e-mails |
| `phone` | `String` | `VARCHAR(20)` | Preserva `+55` e zeros à esquerda |
| `gender` | `enum Gender` | `ENUM('MALE','FEMALE','OTHER')` | Restringe a valores válidos |
| `height` | `BigDecimal` | `DECIMAL(3,2)` | Precisão exata — ex: `1.75` |
| `weight` | `BigDecimal` | `DECIMAL(5,1)` | Precisão exata — ex: `82.5` |

---

## ⚙️ Configuração (`application.properties`)

```properties
# Conexão com o banco de dados MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/crud_db
spring.datasource.username=root
spring.datasource.password=suasenha
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Caminho do Swagger UI no navegador
springdoc.swagger-ui.path=/swagger-ui.html

# Exibe as queries SQL no console (útil para aprendizado — remover em produção)
logging.level.org.springframework.jdbc=DEBUG
```

> ⚠️ Nunca versione senhas reais. Em projetos reais, use variáveis de ambiente ou um arquivo `.env`.

---

## ▶️ Como rodar

### 1. Clonar o repositório
```bash
git clone https://github.com/devDougie/exemplo-api-crud-java.git
cd exemplo-api-crud-java
```

### 2. Configurar o banco de dados
- Certifique-se de que o MySQL está rodando na porta `3306`
- Execute o script SQL de criação do banco e da tabela (seção [Banco de dados](#-banco-de-dados))

### 3. Configurar as credenciais
Abra `src/main/resources/application.properties` e ajuste usuário e senha do MySQL:
```properties
spring.datasource.username=root
spring.datasource.password=suasenha
```

### 4. Rodar a aplicação
No IntelliJ, abra `CrudApplication.java` e clique no botão **▶ Play** ao lado do `main`. Aguarde a mensagem `Started CrudApplication` no console.

### 5. Acessar o Swagger UI
```
http://localhost:8080/swagger-ui.html
```

---

## 🔗 Endpoints

| Método | Endpoint | Descrição | Status de sucesso |
|---|---|---|---|
| `GET` | `/api/persons` | Lista todas as pessoas | `200 OK` |
| `GET` | `/api/persons/{id}` | Busca pessoa por ID | `200 OK` |
| `POST` | `/api/persons` | Cria nova pessoa | `201 Created` |
| `PUT` | `/api/persons/{id}` | Atualiza pessoa completa | `204 No Content` |
| `DELETE` | `/api/persons/{id}` | Remove pessoa | `204 No Content` |

### Exemplo de corpo para POST e PUT

```json
{
  "fullName": "João da Silva",
  "email": "joao.silva@email.com",
  "phone": "+5511999999999",
  "gender": "MALE",
  "height": 1.75,
  "weight": 80.5
}
```

### Exemplo de resposta GET

```json
{
  "id": 1,
  "fullName": "João da Silva",
  "email": "joao.silva@email.com",
  "phone": "+5511999999999",
  "gender": "MALE",
  "height": 1.75,
  "weight": 80.5
}
```

### Exemplo de resposta de erro — 404

```json
{
  "error": "Pessoa com ID 99 não encontrada."
}
```

### Exemplo de resposta de validação — 400

```json
{
  "email": "E-mail inválido",
  "fullName": "Nome é obrigatório"
}
```

---

## 🖥️ Demonstração — Swagger UI

> 💡 **Como adicionar os GIFs:** grave cada operação com o [ScreenToGif](https://www.screentogif.com/) (gratuito), salve os arquivos na pasta `docs/` na raiz do projeto e substitua os blocos abaixo pela sintaxe `![descrição](docs/nome-do-arquivo.gif)`.

**POST — Criar pessoa**
```
[ GIF aqui ]
```

**GET — Listar todas**
```
[ GIF aqui ]
```

**GET — Buscar por ID**
```
[ GIF aqui ]
```

**PUT — Atualizar**
```
[ GIF aqui ]
```

**DELETE — Remover**
```
[ GIF aqui ]
```

---

## 🛡️ Proteção contra SQL Injection

Todas as queries utilizam `PreparedStatement` via `JdbcTemplate`. Os valores são passados como argumentos separados — nunca concatenados diretamente na query:

```java
// ✅ Seguro — PreparedStatement com parâmetros separados
String sql = "SELECT * FROM persons WHERE id = ?";
jdbc.query(sql, rowMapper, id);

// ❌ Vulnerável — nunca faça isso
String sql = "SELECT * FROM persons WHERE id = " + id;
```

---

## 📐 Fluxo de uma requisição

```
Requisição HTTP
      │
      ▼
[PersonController]   @RestController — recebe HTTP, aciona @Valid, delega ao Service
      │
      ▼
[PersonService]      @Service — valida existência, converte DTO ↔ Model, aplica regras
      │
      ▼
[PersonRepository]   @Repository — executa SQL via JdbcTemplate (PreparedStatement)
      │
      ▼
   [MySQL]           Banco de dados rodando em localhost:3306/crud_db
```

---

## 🐙 Subindo o projeto para o GitHub

### Pré-requisito
Certifique-se de que o Git está instalado na máquina:
```bash
git --version
```

### Comandos — primeira vez (repositório novo)

Execute dentro da pasta raiz do projeto (`crud/`) no terminal:

```bash
# 1. Inicializa o repositório Git local
git init

# 2. Adiciona todos os arquivos ao stage (respeita o .gitignore)
git add .

# 3. Cria o primeiro commit
git commit -m "feat: projeto inicial CRUD de pessoas"

# 4. Renomeia a branch principal para 'main'
git branch -M main

# 5. Conecta ao repositório remoto no GitHub
git remote add origin https://github.com/[nome-do-perfil]/[nome-do-repositorio].git

# 6. Envia o código para o GitHub
git push -u origin main
```

> 💡 O `-u` no `git push` configura o rastreamento da branch — nos próximos pushes basta digitar `git push`.

### Comandos — atualizações futuras

Sempre que fizer alterações no projeto:

```bash
# Verifica quais arquivos foram modificados
git status

# Adiciona as alterações ao stage
git add .

# Cria um commit com descrição da mudança
git commit -m "descrição do que foi alterado"

# Envia para o GitHub
git push
```

### Boas práticas de mensagem de commit

| Prefixo | Uso |
|---|---|
| `feat:` | Nova funcionalidade |
| `fix:` | Correção de bug |
| `docs:` | Alteração em documentação |
| `refactor:` | Refatoração sem mudar comportamento |
| `chore:` | Tarefas de manutenção (deps, config) |

Exemplo: `git commit -m "docs: adiciona GIFs de demonstração no README"`

---

## 📦 Dependências (`pom.xml`)

| Dependência | Versão | Função |
|---|---|---|
| `spring-boot-starter-web` | gerenciada pelo parent | Servidor HTTP + endpoints REST |
| `spring-boot-starter-data-jdbc` | gerenciada pelo parent | JdbcTemplate para acesso ao banco |
| `spring-boot-starter-validation` | gerenciada pelo parent | Validação de dados de entrada |
| `mysql-connector-j` | gerenciada pelo parent | Driver JDBC do MySQL |
| `springdoc-openapi-starter-webmvc-ui` | `2.8.16` | Swagger UI (adicionada manualmente) |
| `spring-boot-starter-test` | gerenciada pelo parent | Testes (JUnit 5, Mockito) |
