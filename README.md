# 📚 API de Biblioteca Robusta (Spring Boot 3 + MongoDB)

Uma API REST completa para gerenciamento de biblioteca, desenvolvida com **Spring Boot 3** (Java 21 LTS) e **MongoDB**. O projeto segue rigorosamente o padrão de arquitetura em camadas (Model, Repository, Service, Controller) e implementa regras de negócio complexas.

## 🚀 Funcionalidades Implementadas

✅ **Cadastro de Livros:** CRUD completo (Criação, Leitura, Atualização e Deleção com validação de empréstimo).
✅ **Controle de Empréstimos:** Empréstimo, Devolução e **Renovação** com limite (máx. 1).
✅ **Regras de Negócio:** Não permite empréstimo de livro indisponível e impede a deleção de livros emprestados.
✅ **Pesquisa Avançada:** Busca por Título, Autor, Categoria e ISBN.
✅ **Gestão de Atrasos:** Filtro para listar empréstimos ativos, finalizados e **atrasados**.
✅ **Dashboard:** Estatísticas e resumos em tempo real (total de livros, ativos, atrasados, taxa de atraso).

---

## 🛠️ Tecnologias Utilizadas

* **Java 21 LTS**
* **Spring Boot 3.x**
* **Spring Web** (RESTful API)
* **Spring Data MongoDB**
* **Lombok** (para reduzir o código boilerplate)
* **MongoDB**
* **Maven**

---

## ⚙️ Configuração e Execução

### Pré-requisitos

1.  **Java 21** (ou superior) instalado.
2.  **MongoDB** rodando (localmente na porta padrão 27017 ou via Atlas).
3.  **Maven** instalado.

### Configuração do Banco de Dados

Certifique-se de que suas credenciais e o nome do banco de dados estão configurados corretamente no arquivo `src/main/resources/application.properties` (especialmente se o seu MongoDB exigir autenticação):

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/minha_biblioteca
# Se autenticação for NECESSÁRIA:
# spring.data.mongodb.username=seuUsuario
# spring.data.mongodb.password=suaSenha
# spring.data.mongodb.authentication-database=admin 
Inicialização e Data Seeding
O projeto inclui um DataInitializer (@CommandLineRunner) que popula a coleção de livros automaticamente na inicialização, facilitando os testes.

Execute a aplicação via Maven:

Bash

mvn spring-boot:run
A API estará disponível em: http://localhost:8080

📋 Endpoints da API
O caminho base para todos os endpoints é: /api/v1

📖 Livros (Base: /api/v1/books)
Método	Endpoint	Descrição
GET	/books	Lista todos os livros.
POST	/books	Cadastra um novo livro.
GET	/books/{id}	Busca livro por ID.
PUT	/books/{id}	Atualiza um livro existente.
DELETE	/books/{id}	Remove um livro (se não estiver emprestado - Regra de Negócio).
GET	/books/disponiveis	Lista livros disponíveis.
GET	/books/emprestados	Lista livros atualmente emprestados.
GET	/books/search/titulo/{titulo}	Busca livros por Título (parcial).
GET	/books/search/autor/{autor}	Busca livros por Autor (parcial).
GET	/books/search/categoria/{categoria}	Busca livros por Categoria.
GET	/books/search/isbn/{isbn}	Busca livro por ISBN.

Exportar para as Planilhas
📋 Empréstimos (Base: /api/v1/loans)
Método	Endpoint	Descrição
GET	/loans	Lista o histórico completo de empréstimos.
POST	/loans	Realiza um novo empréstimo.
PUT	/loans/{bookId}/devolucao	Registra a devolução do livro.
PUT	/loans/{loanId}/renovar	Renova um empréstimo ativo (limite de 1 renovação).
GET	/loans/ativos	Lista todos os empréstimos ativos.
GET	/loans/finalizados	Lista todos os empréstimos já devolvidos.
GET	/loans/atrasados	Lista todos os empréstimos que estão atrasados.
GET	/loans/usuario/{email}/ativos	Empréstimos ativos de um usuário por e-mail.

Exportar para as Planilhas
📊 Dashboard (Base: /api/v1/dashboard)
Método	Endpoint	Descrição
GET	/dashboard	Informações completas do dashboard (resumo).
GET	/dashboard/resumo	Resumo estatístico (o mesmo que /dashboard).

Exportar para as Planilhas
📝 Exemplos de Uso
1. Realizar um Empréstimo (POST)
Necessário: O bookId de um livro disponível.

POST http://localhost:8080/api/v1/loans

JSON

{
  "bookId": "651a1b2c3d4e5f6789012345",
  "nomeLeitor": "João Silva",
  "emailUsuario": "joao@email.com"
}
2. Devolver um Livro (PUT)
Necessário: O bookId do livro que está emprestado.

PUT http://localhost:8080/api/v1/loans/651a1b2c3d4e5f6789012345/devolucao

🏗️ Estrutura do Projeto
O projeto segue uma arquitetura robusta e modular, separando a lógica por domínio:

src/main/java/com/seuprojeto/biblioteca_avancada/
├── BibliotecaAvancadaApplication.java 
├── DataInitializer.java            # Configuração de dados iniciais
├── book/                           # Módulos relacionados a Livros
│   ├── controller/ 
│   ├── model/ 
│   ├── repository/ 
│   └── service/ 
├── loan/                           # Módulos relacionados a Empréstimos
│   ├── controller/ 
│   ├── model/ 
│   ├── repository/ 
│   └── service/ 
└── dashboard/                      # Módulos de Estatísticas
    ├── controller/ 
    ├── model/ 
    └── service/ 
