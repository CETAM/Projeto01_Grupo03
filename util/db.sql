create table autores (
	idAutor int auto_increment primary key,
    nome varchar(200) not null
);

create table editoras (
	idEditora int auto_increment primary key,
    nome varchar(200) not null
);

create table livros (
	idLivro int auto_increment primary key,
    titulo varchar(200) not null,
    ano int not null,
    idAutor int not null,
    idEditora int not null,
    
    foreign key (idAutor) references autores(idAutor),
    foreign key (idEditora) references editoras(idEditora)
);

create table alunos (
	idAluno int auto_increment primary key,
    nome varchar(200) not null,
    matricula varchar(50) not null unique,
    email varchar(150) not null
);

create table emprestimos (
	idEmprestimo int auto_increment primary key,
    idAluno int not null,
    idLivro int not null,
    dataEmprestimo date not null,
    dataDevolucao date null,
    
    foreign key (idAluno) references alunos(idAluno),
    foreign key (idLivro) references livros(idLivro)
);

create table multas (
	idMulta int auto_increment primary key,
    idEmprestimo int not null,
    valor decimal(10,2) not null,
    
    foreign key (idEmprestimo) references emprestimos(idEmprestimo)
);
