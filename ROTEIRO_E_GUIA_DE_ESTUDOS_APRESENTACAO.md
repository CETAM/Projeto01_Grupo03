# 🎓 GUIA COMPLETO DE ESTUDOS E ROTEIRO DE APRESENTAÇÃO
## CETAM – Projeto de Sistemas Computacionais Web
**Projeto 1:** Sistema de Controle de Biblioteca  
**Equipe:** Grupo 03  
**Data da Apresentação:** 27/08/2026  
**Tempo Total Estimado:** 15 a 18 minutos (Máximo: 20 min)  

---

# 📑 SUMÁRIO
1. [Visão Geral e Distribuição do Tempo](#1-visão-geral-e-distribuição-do-tempo)
2. [Etapa 1: Contextualização (1 min)](#2-etapa-1-contextualização-1-min)
3. [Etapa 2: Apresentação da Arquitetura (2 min)](#3-etapa-2-apresentação-da-arquitetura-2-min)
4. [Etapa 3: Demonstração Funcional ao Vivo (5 a 7 min)](#4-etapa-3-demonstração-funcional-ao-vivo-5-a-7-min)
5. [Etapa 4: Demonstração Técnica no Código (2 a 3 min)](#5-etapa-4-demonstração-técnica-no-código-2-a-3-min)
6. [Etapa 5: Banco de Dados e Relacionamentos (1 a 2 min)](#6-etapa-5-banco-de-dados-e-relacionamentos-1-a-2-min)
7. [Etapa 6: Banco de Respostas para Perguntas do Professor](#7-etapa-6-banco-de-respostas-para-perguntas-do-professor)
8. [Tópicos Especiais e Diferenciais Técnicos do Projeto](#8-tópicos-especiais-e-diferenciais-técnicos-do-projeto)
   - [Por que usamos @RestController além de @Controller?](#81-por-que-usamos-restcontroller-além-de-controller)
   - [Por que criamos DTOs (Data Transfer Objects)?](#82-por-que-criamos-dtos-data-transfer-objects)
   - [Como funciona a Sidebar Reutilizável (Thymeleaf Fragments)?](#83-como-funciona-a-sidebar-reutilizável-thymeleaf-fragments)
   - [Como os Gráficos do Dashboard foram feitos e funcionam?](#84-como-os-gráficos-do-dashboard-foram-feitos-e-funcionam)
   - [Como funciona a exportação de relatórios em PDF?](#85-como-funciona-a-exportação-de-relatórios-em-pdf)

---

# 1. Visão Geral e Distribuição do Tempo

| Etapa | Foco | Tempo Estimado | Quem Fala? |
| :--- | :--- | :---: | :---: |
| **1. Contextualização** | Problema que o sistema resolve | ~1 min | Integrante 1 |
| **2. Arquitetura** | Camadas Spring Boot e responsabilidades | ~2 min | Integrante 1 |
| **3. Demonstração Funcional** | Executar o fluxo de negócio completo na tela | ~5 a 7 min | Integrante 2 |
| **4. Demonstração Técnica** | 3 pontos de código (@Entity, @Repository, @Controller) | ~2 a 3 min | Integrante 3 |
| **5. Banco de Dados** | DER, tabelas e relacionamentos | ~1 a 2 min | Integrante 3 |
| **6. Perguntas e Respostas** | Defesa técnica e perguntas da banca | ~2 a 3 min | Todos |

---

# 2. Etapa 1: Contextualização (1 min)

### 🎯 Objetivo:
Explicar claramente o propósito do sistema e o problema do mundo real que ele soluciona, **sem mostrar código nem telas ainda**.

### 🗣️ Roteiro de Fala Sugerido:
> *"Bom dia/Boa noite, professor e colegas. O nosso projeto é o **Sistema de Controle de Biblioteca**, desenvolvido pelo **Grupo 03**.*
>
> *O objetivo central da aplicação é automatizar e profissionalizar a gestão de um acervo bibliotecário escolar ou acadêmico. O sistema resolve os gargalos manuais mais comuns: controla o cadastro completo de obras, autores, editoras, categorias e alunos; gerencia o ciclo de vida dos empréstimos e devoluções; controla em tempo real o estoque de exemplares físicos disponíveis; detecta automaticamente situações de inadimplência e atraso; e realiza a gestão financeira de multas com relatórios analíticos e demonstrativos em PDF."*

---

# 3. Etapa 2: Apresentação da Arquitetura (2 min)

### 🎯 Objetivo:
Apresentar o desenho da arquitetura em camadas do Spring Boot e destacar a responsabilidade de cada camada de forma objetiva.

```
┌────────────────────────────────────────────────────────┐
│             NAVEGADOR WEB (Cliente)                    │
└───────────────────────┬────────────────────────────────┘
                        │ Requisições HTTP (GET, POST, etc.)
                        ▼
┌────────────────────────────────────────────────────────┐
│  CONTROLLERS (Spring MVC) & REST CONTROLLERS (API)     │
│  - Recebe requisições, valida parâmetros e direciona   │
└───────────────────────┬────────────────────────────────┘
                        │ Invoca regras de negócio
                        ▼
┌────────────────────────────────────────────────────────┐
│  SERVICES (Camada de Negócio e Transações)             │
│  - Validações, cálculos de multas, controle de estoque │
└───────────────────────┬────────────────────────────────┘
                        │ Métodos CRUD e consultas customizadas
                        ▼
┌────────────────────────────────────────────────────────┐
│  REPOSITORIES (Spring Data JPA)                        │
│  - Comunicação e persistência com o Banco de Dados     │
└───────────────────────┬────────────────────────────────┘
                        │ Mapeamento Objeto-Relacional (ORM)
                        ▼
┌────────────────────────────────────────────────────────┐
│  MODEL / ENTIDADES JPA (Hibernate) <───> BANCO (MySQL) │
└────────────────────────────────────────────────────────┘
```

### 🗣️ Roteiro de Fala Sugerido:
> *"Para garantir escalabilidade, testabilidade e baixo acoplamento, estruturamos o projeto no padrão arquitetural clássico do Spring Boot em 5 camadas principais:*
>
> 1. **Controller (`@Controller` e `@RestController`):** *Porta de entrada das requisições HTTP. Trata as entradas do usuário e direciona para as páginas HTML ou respostas JSON para modais assíncronos.*
> 2. **Service (`@Service`):** *Coração do sistema. Concentra todas as regras de negócio, verificações de duplicidade, cálculos de prazos/multas e controle transacional atômico (`@Transactional`).*
> 3. **Repository (`@Repository` / `JpaRepository`):** *Camada de abstração de dados que elimina SQL manual para operações CRUD e consultas derivadas.*
> 4. **Entity / Model (`@Entity`):** *Mapeamento das entidades relacionais com anotações JPA e regras de integridade (como campos únicos e não nulos).*
> 5. **Thymeleaf + Bootstrap:** *Camada de visualização (Server-Side Rendering), montando telas responsivas e com feedback visual instantâneo."*

---

# 4. Etapa 3: Demonstração Funcional ao Vivo (5 a 7 min)

### 🎯 Objetivo:
Executar um **roteiro de negócio sequencial** em `http://localhost:8080`, demonstrando o processo completo de ponta a ponta sem cliques aleatórios.

### 📌 Sequência de Demonstração (Passo a Passo):

#### Passo 1: Cadastro de Autor e Categoria com Validação
- Acesse o menu **Autores** ➔ Clique em **Novo Autor**.
- Cadastre um autor (ex: *"Graciliano Ramos"*).
- *(Dica de Impacto)*: Tente cadastrar o mesmo nome novamente. Mostre o alerta vermelho na tela: *"Já existe um autor cadastrado com o nome..."*. Isso demonstra validação prévia de duplicidade!

#### Passo 2: Cadastro do Livro e Controle de Exemplares
- Acesse **Livros** ➔ **Novo Livro**.
- Preencha: Título *"Vidas Secas"*, ISBN *"978-85-01-00001-1"*, Ano *1938*, Quantidade de Exemplares: **3**, selecione Autor, Editora e Categoria.
- Mostre que o livro foi cadastrado com **3 exemplares disponíveis** em estoque.

#### Passo 3: Cadastro do Aluno
- Acesse **Alunos** ➔ **Novo Aluno**.
- Preencha: Nome *"Lucas Silva"*, Matrícula *"2026100"*, E-mail *"lucas.silva@escola.edu.br"*.

#### Passo 4: Realização de Empréstimo (Atualização de Estoque)
- Acesse **Empréstimos** ➔ **Novo Empréstimo**.
- Selecione o aluno *"Lucas Silva"* e o livro *"Vidas Secas"*.
- Salve o empréstimo. Mostre o status **`ATIVO`** com prazo de devolução de 14 dias.
- Volte em **Livros** e aponte para a quantidade: o estoque caiu de **3** para **2** exemplares disponíveis automaticamente.

#### Passo 5: Dashboard e Relatórios em Tempo Real
- Acesse o **Dashboard (Início)**:
  - Mostre os cards de KPIs (Total de Livros, Empréstimos Ativos, Taxa de Circulação).
  - Mostre os gráficos dinâmicos de movimentação semanal e distribuição por categorias.
- Acesse **Relatórios** ➔ Mostre a tela de **Empréstimos em Atraso** e clique em **Exportar PDF** para abrir o relatório gerado em tempo real.

#### Passo 6: Devolução e Gestão de Multas
- Acesse **Empréstimos** ➔ No empréstimo do aluno, clique no botão **Devolver**.
- O sistema conclui o empréstimo (`Status: DEVOLVIDO`), registra a data de devolução e repõe o exemplar no estoque (voltando para **3** disponíveis).
- Vá em **Multas**: mostre como as multas são gerenciadas e demonstre a ação de **"Receber"** para liquidar o pagamento.

---

# 5. Etapa 4: Demonstração Técnica no Código (2 a 3 min)

### 🎯 Objetivo:
Abrir a IDE (VS Code / IntelliJ) e explicar com clareza **apenas os 3 pontos técnicos centrais** solicitados pelo professor.

---

### 🔹 Ponto 1: `@Entity` (Abra `Aluno.java` ou `Livro.java`)
```java
@Entity
@Table(name = "alunos")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aluno")
    private Long id;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(nullable = false, unique = true, length = 50)
    private String matricula;

    @Column(unique = true, length = 150)
    private String email;
    ...
```
* **O que explicar:**
  > *"A anotação `@Entity` informa ao JPA/Hibernate que esta classe é uma tabela no banco de dados. O `@Id` define a chave primária, o `@GeneratedValue(strategy = GenerationType.IDENTITY)` delega o auto-incremento ao MySQL, e usamos restrições como `@Column(unique = true)` para garantir integridade relacional diretamente na definição do modelo."*

---

### 🔹 Ponto 2: `@Repository` (Abra `EmprestimoRepository.java`)
```java
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    List<Emprestimo> findByStatus(StatusEmprestimo status);

    @Query("SELECT e FROM Emprestimo e WHERE e.dataDevolucao IS NULL AND " +
           "(e.status = 'ATRASADO' OR (e.status = 'ATIVO' AND e.dataPrevisaoDevolucao < :hoje))")
    List<Emprestimo> findEmprestimosEmAtrasoEmAberto(@Param("hoje") LocalDate hoje);
}
```
* **O que explicar:**
  > *"O `EmprestimoRepository` estende `JpaRepository<Emprestimo, Long>`. Com isso, ganhamos todas as operações CRUD básicas sem escrever uma única linha de SQL manual. Além disso, utilizamos consultas derivadas por assinatura (`findByStatus`) e consultas JPQL customizadas com `@Query` para relatórios e filtros otimizados."*

---

### 🔹 Ponto 3: `@Controller` e o Fluxo de Requisição Spring MVC (Abra `EmprestimoController.java`)
```java
@GetMapping
public String listar(Model model) {
    List<Emprestimo> emprestimos = emprestimoService.listarTodos(null);
    model.addAttribute("emprestimos", emprestimos);
    return "emprestimos/listar";
}
```
* **O que explicar (O Ciclo da Requisição):**
  > *"O ciclo de vida do Spring MVC ocorre da seguinte forma:*
  > 1. *O **Navegador** faz uma requisição `GET /emprestimos`.*
  > 2. *O **Controller** intercepta a rota através do `@GetMapping`.*
  > 3. *O Controller solicita a lista de dados ao **Service**, que consulta o **Repository** e o **Banco de Dados**.*
  > 4. *O Controller popula o objeto `Model` com os dados encontrados.*
  > 5. *O Controller retorna o nome lógico da view (`"emprestimos/listar"`).*
  > 6. *O **Thymeleaf** faz o parse do template HTML, substitui as tags dinâmicas pelos dados do Model e entrega o HTML final renderizado ao navegador."*

---

# 6. Etapa 5: Banco de Dados e Relacionamentos (1 a 2 min)

### 🎯 Objetivo:
Apresentar o DER (`modelo-entidade-relacionamento.png`) e demonstrar domínio sobre a modelagem relacional e cardinalidades.

```
┌──────────┐         ┌───────────┐         ┌────────────┐
│  AUTOR   │ 1 ──── N│   LIVRO   │N ──── 1 │ CATEGORIA  │
└──────────┘         └─────┬─────┘         └────────────┘
                           │ 1
                           │
                           │ N
┌──────────┐ 1       N ┌───┴──────────┐ 1       1 ┌────────────┐
│  ALUNO   ├───────────┤  EMPRÉSTIMO  ├───────────┤   MULTA    │
└──────────┘           └──────────────┘           └────────────┘
```

### 🗣️ O que destacar:
1. **Relações 1:N no Livro:** *"Um Autor, uma Editora e uma Categoria possuem vários Livros (`1:N`). Na classe `Livro`, utilizamos `@ManyToOne` com `@JoinColumn` para referenciar as chaves estrangeiras."*
2. **Empréstimo como Entidade Associativa:** *"O `Empréstimo` conecta o `Aluno` (`N:1`) ao `Livro` (`N:1`), agregando atributos próprios da transação: `dataEmprestimo`, `dataPrevisaoDevolucao`, `dataDevolucao` e `status`."*
3. **Relação 1:1 com Multa:** *"A `Multa` possui relacionamento `@OneToOne` com o `Empréstimo`, garantindo que cada atraso possua no máximo uma penalidade financeira associada."*

---

# 7. Etapa 6: Banco de Respostas para Perguntas do Professor

---

### ❓ Pergunta 1: *"O que aconteceria se o usuário tentasse cadastrar um aluno ou livro duplicado (mesmo e-mail, matrícula ou ISBN)?"*
* **Resposta Ideal:**
  > *"O sistema trabalha com validação em duas camadas:*
  > 1. *Na camada de **Service** (`AlunoService` / `LivroService`), nós consultamos previamente o banco via métodos como `existsByMatricula()` ou `existsByIsbn()`. Se duplicado, lançamos uma `IllegalArgumentException` com mensagem clara.*
  > 2. *No **Controller**, capturamos a exceção em um bloco `try-catch`, mantemos os campos preenchidos no formulário (para o usuário não perder a digitação) e enviamos uma `mensagemErro` que o Thymeleaf renderiza como um alerta vermelho no topo.*
  > 3. *E no **Banco de Dados**, as colunas possuem a constraint `UNIQUE`, garantindo que nenhuma duplicação passe mesmo em acessos concorrentes."*

---

### ❓ Pergunta 2: *"Qual é a responsabilidade do Service no projeto? Por que não chamar o Repository direto no Controller?"*
* **Resposta Ideal:**
  > *"O `Service` é onde reside a inteligência e as regras de negócio do sistema. Chamar o Repository direto no Controller violaria o princípio de responsabilidade única (SRP). É no Service que centralizamos:*
  > - *Validação de regras (ex: impedir empréstimo para aluno inadimplente ou livro sem estoque);*
  > - *Manipulação de estado (decrementar e incrementar estoque de exemplares);*
  > - *Cálculo de penalidades (dias de atraso e valor de multas);*
  > - *Controle de transações com `@Transactional`, garantindo que se uma etapa falhar, nenhuma alteração parcial seja gravada no banco."*

---

### ❓ Pergunta 3: *"Se precisássemos adicionar uma funcionalidade de 'Reserva de Livros' amanhã, o que mudaríamos?"*
* **Resposta Ideal:**
  > *"Graças à arquitetura em camadas desacopladas, a inclusão seria cirúrgica e organizada:*
  > 1. **Model:** *Criaríamos `Reserva.java` com `@Entity`, `@Id` e `@ManyToOne` para Aluno e Livro.*
  > 2. **Repository:** *Criaríamos `ReservaRepository.java` estendendo `JpaRepository`.*
  > 3. **Service:** *Criaríamos `ReservaService.java` com as regras (ex: só permitir reserva se o livro tiver 0 exemplares disponíveis).*
  > 4. **Controller:** *Criaríamos `ReservaController.java` mapeando as rotas `/reservas`.*
  > 5. **View:** *Criaríamos os templates em `src/main/resources/templates/reservas/` e adicionaríamos o item no fragmento da Sidebar."*

---

# 8. Tópicos Especiais e Diferenciais Técnicos do Projeto

---

### 8.1 Por que usamos `@RestController` além de `@Controller`?
* **Motivo:**
  - `@Controller`: Utilizado para rotas que renderizam **páginas completas em HTML** via Thymeleaf (ex: `/livros`, `/alunos`, `/emprestimos`).
  - `@RestController`: Utilizado para rotas que retornam **dados puros em formato JSON** (ex: `/api/autores`, `/api/editoras`, `/api/categorias`).
* **Aplicação Prática no Projeto:**
  - Na tela de cadastro de Livros, existem botões de **cadastro rápido via Modal** para criar Autores ou Editoras sem precisar sair da página.
  - O JavaScript dispara uma requisição `fetch()` para o `@RestController`, que salva a entidade e retorna o objeto em JSON com status `201 CREATED` (ou `409 CONFLICT` se duplicado), adicionando a nova opção dinamicamente no `<select>` da tela via JavaScript.

---

### 8.2 Por que criamos DTOs (Data Transfer Objects)?
* **Motivo:**
  - As entidades JPA (`@Entity`) são vinculadas ao banco e possuem relacionamentos bidirecionais (que podem causar loops infinitos de serialização JSON) e overhead de carregamento.
  - Os **DTOs** (`DashboardDTO`, `RelatorioGeralDTO`, `ResumoFinanceiroDTO`, `LivroMaisEmprestadoDTO`) são classes leves e imutáveis criadas sob medida para transportar dados consolidados entre o Service e a View.
* **Benefícios:**
  1. **Performance:** Permite consultas JPQL com projeção direta de agregações (`COUNT`, `SUM`) sem instanciar entidades completas na memória.
  2. **Segurança e Desacoplamento:** Não expõe a estrutura interna das tabelas para a camada de apresentação.

---

### 8.3 Como funciona a Sidebar Reutilizável (Thymeleaf Fragments)?
* **Arquivo:** `templates/fragments/sidebar.html`
* **Mecanismo:**
  - Definida como um fragmento reutilizável: `<th:block th:fragment="sidebar(activePage)">`.
  - Cada página do sistema inclui a barra lateral com uma única linha:
    ```html
    <div th:replace="~{fragments/sidebar :: sidebar('emprestimos')}"></div>
    ```
  - O parâmetro `activePage` ativa a classe CSS `.active` dinamicamente no item de menu correspondente.
  - Possui suporte a responsividade mobile com menu gaveta (offcanvas) e backdrop escurecido controlado por JavaScript puro.

---

### 8.4 Como os Gráficos do Dashboard foram feitos e funcionam?
* **Tecnologia:** [Chart.js](https://www.chartjs.org/) no frontend + DTO agregado no backend.
* **Fluxo:**
  1. O `RelatorioService.obterDadosDashboard()` calcula os números consolidados dos últimos 7 dias (empréstimos e devoluções diárias) e a distribuição de títulos por categoria.
  2. Esses dados são passados no `DashboardDTO` para a tela `dashboard/index.html`.
  3. O Thymeleaf injeta os arrays de dados no script:
     ```javascript
     const labels = /*[[${dashboard.diasGraficoLabels}]]*/ [];
     const dataEmprestimos = /*[[${dashboard.emprestimosPorDia}]]*/ [];
     ```
  4. O **Chart.js** inicializa os gráficos renderizando elementos interativos no `<canvas>` com tooltips e animações suaves.

---

### 8.5 Como funciona a exportação de relatórios em PDF?
* **Tecnologia:** [OpenHTMLtoPDF](https://github.com/danfickle/openhtmltopdf) + Spring Boot.
* **Fluxo:**
  1. O usuário clica em **"Exportar PDF"** na tela de relatórios.
  2. O `RelatorioController` aciona o `PdfReportService.gerarPdf()`.
  3. O motor do **Thymeleaf** processa o template HTML específico para impressão (ex: `relatorios/pdf/atrasados-pdf.html`), com layout otimizado para folhas A4 e regras `@page`.
  4. A biblioteca **OpenHTMLtoPDF** converte o HTML e CSS diretamente em um fluxo binário de bytes (`byte[]`).
  5. O Spring retorna o PDF com cabeçalho `Content-Disposition: inline`, permitindo abrir e visualizar o PDF diretamente na aba do navegador.

---

### 🏆 Critérios de Avaliação do Professor (Checklist de Sucesso):
- [x] **Funcionamento do sistema (25%):** Fluxo de empréstimo, devolução e multas 100% funcional.
- [x] **Implementação Spring Boot (20%):** Controllers, Services, Repositories e injeção de dependências corretas.
- [x] **Persistência / JPA / Banco (15%):** Entidades mapeadas, constraints `UNIQUE`, relacionamentos `@ManyToOne`/`@OneToOne`.
- [x] **Arquitetura e Organização (15%):** Separação estrita em camadas (Controller ➔ Service ➔ Repository ➔ Model).
- [x] **Interface / Thymeleaf / Bootstrap (10%):** Sidebar componentizada, layout responsivo, alertas de feedback amigáveis.
- [x] **Regras de negócio (10%):** Bloqueio de alunos inadimplentes, cálculo diário de multas, controle de estoque de exemplares.
- [x] **Apresentação e Domínio Técnico (5%):** Roteiro cronometrado, divisão clara entre os membros e respostas seguras.
