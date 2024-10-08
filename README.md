
# gestao-bliotetca-api

API para uma aplicação de gerenciar bliotetca

BASE URL:
http://localhost:8080/api/biblioteca

Para iniciar a aplicação, suba um banco Postgres e execute o script: `src/main/resources/migration.sql` 

## Stack
- SpringBoot
- Java 17
- Postgres



## Recursos

### Empréstimos

#### Busca todos os emprestimos

```http
  GET /api/biblioteca/emprestimos
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `limit` | `integer` | Limite do tamanho da página |
| `page` | `integer` | Número da página |



#### Cria um empréstimo

```http
  POST /api/biblioteca/emprestimos
```

Exemplo body:
```
{
	"usuarioId": 2,
	"livroId": 8,
	"dataDevolucao": "2024-10-11T12:00:00"
}
```

#### Atualiza um empréstimo

```http
  PUT /api/biblioteca/emprestimos
```
Exemplo body:
```
{
	"dataDevolucao": "2024-10-11T12:00:00",
	"status": "DEVOLVIDO"
}
```

### Livros

#### Busca todos os livros
```http
  GET /api/biblioteca/livros
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `limit` | `integer` | Limite do tamanho da página |
| `page` | `integer` | Número da página |

#### Cria um livro
```http
  POST /api/biblioteca/livros
```
Exemplo body:
```
{
	"titulo": "Musica 5",
	"autor": "Autor Teste",
	"isbn": "8532511015",
	"dataPublicacao": "2000-04-07",
	"categoria": "MUSICA"
}
```
#### Busca um livro

```http
  GET /api/biblioteca/livros/{id}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `integer` | ID do livro |

#### Atualiza um livro

```http
  PUT /api/biblioteca/livros/{id}
```
Exemplo body:
```
{
	"titulo": "Harry Potter",
	"autor": "J.K Rowling",
	"isbn": "ISBN-10, 8532511015",
	"dataPublicacao": "2000-04-07",
	"categoria": "FICCAO"
}
```
#### Exclui um livro
```http
  DELETE /api/biblioteca/livros/{id}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `integer` | ID do livro |

#### Procura recomendações
```http
  DELETE /api/biblioteca/livros/recomendacoes-usuario/{usuarioId}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `usuarioId` | `integer` | ID do usuário |

#### Procura livros integração Google Books

```http
  GET /api/biblioteca/livros/integracao/busca-titulo
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `titulo` | `string` | Título do livro |

### Usuários

#### Busca todos os usuários
```http
  GET /api/biblioteca/usuarios
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `limit` | `integer` | Limite do tamanho da página |
| `page` | `integer` | Número da página |
#### Cria um usuário
```http
  POST /api/biblioteca/usuarios
```
Exemplo body:
```
{
	"nome": "Arthur Dale",
	"email": "teste@gmail.com",
	"dataCadastro": "2024-07-01T00:00:00",
	"telefone": "48 99955-0000"
}
```
#### Busca um usuário

```http
  GET /api/biblioteca/usuario/{id}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `integer` | ID do usuario |

#### Atualiza um usuário
```http
  PUT /api/biblioteca/usuarios/{id}
```
Exemplo body:
```
{
	"nome": "Arthur Dal sdadsade",
	"email": "teste@gmail.com.br",
	"dataCadastro": "2024-07-01T00:00:00",
	"telefone": "48 99955-0000"
}
```
#### Exclui um usuário

```http
  DELETE /api/biblioteca/usuario/{id}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `integer` | ID do usuario |


