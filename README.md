# Sistema de Gerenciamento de Mercadinho

Para o mercadinho do Sr. Pedrinho, localizado no bairro Abolição 7, este sistema foi desenvolvido para oferecer um controle informatizado de estoque e vendas desde o primeiro dia. O software auxilia no gerenciamento dos recursos do mercadinho, garantindo organização e eficiência.

---

## 🚀 Tecnologias Utilizadas

- **Java 17**: Linguagem principal da aplicação
- **JavaFX**: Interface gráfica (GUI)
- **Hibernate**: ORM para persistência de dados
- **PostgreSQL**: Banco de dados relacional
- **Maven**: Gerenciamento de dependências e build
- **Docker & Docker Compose**: Gerenciamento do ambiente de banco de dados

---

## ✅ Requisitos

- JDK 17 ou superior
- Maven 3.8+
- Docker
- Docker Compose

---

## ⚙️ Configuração e Execução

### 1. Clonando o Projeto

Clone o repositório e entre na pasta:

```bash
git clone https://github.com/LuizFelipePSilva/projeto-poo-grupo03.git
cd projeto-poo-grupo03
```

### 2. Banco de Dados com Docker Compose

O banco de dados Postgres é gerenciado via Docker Compose para garantir ambiente consistente.

No terminal, na raiz do projeto (onde está o `docker-compose.yml`), execute:

```bash
docker-compose up -d
```

### 3. Executando o projeto com Maven

Apos entrar na pasta /projeto-poo-grupo03, execute o seguinte comando
```bash
cd Projeto-poo
```

Agora execute o comando

```bash
mvn clean javafx:run
```

Pronto o projeto estára rodando na sua maquina.

### 4. Parando o ambiente
```bash
cd ..
docker-compose down
```