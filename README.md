# Global Solution 2026/1 - SOA e Mobile

Projeto desenvolvido para a **Global Solution 2026/1**, integrando as disciplinas de **Arquitetura Orientada a Serviços (SOA)** e **Desenvolvimento Mobile**.

A solução proposta foi o **OrbitGuard**, uma plataforma para monitoramento de riscos climáticos composta por:

* **Backend:** API REST desenvolvida com Spring Boot.
* **Frontend:** Aplicativo mobile desenvolvido com React Native e Expo.

## Estrutura do Repositório

```text
GS-3ESPS/
│
├── backend/
│   └── OrbitGuard API
│
├── mobile/
│   └── OrbitGuard Mobile
│
└── README.md
```

## Projetos

### Backend - OrbitGuard API

API REST responsável por:

* Autenticação de usuários
* Gerenciamento de locais monitorados
* Consulta de dados climáticos
* Geração de relatórios de risco
* Persistência de histórico

Documentação completa disponível em:

```text
backend/README.md
```

---

### Frontend - OrbitGuard Mobile

Aplicativo mobile responsável por:

* Login e cadastro
* Cadastro e consulta de locais
* Visualização de relatórios de risco
* Histórico de consultas
* Cache local para uso offline

Documentação completa disponível em:

```text
mobile/README.md
```

---

## Tecnologias Utilizadas

### Backend

* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA
* JWT
* H2 Database
* Swagger/OpenAPI

### Frontend

* React Native
* Expo
* TypeScript
* React Navigation
* Axios
* AsyncStorage

---

## Como Executar

Cada projeto possui instruções próprias de instalação e execução.

### Backend

```bash
cd backend
```

Consultar:

```text
backend/README.md
```

### Mobile

```bash
cd mobile
```

Consultar:

```text
mobile/README.md
```

---

## Integrantes

| Nome            | RM |
| --------------- | -- |
| Lucas Garcia | RM554070 |
| Enzzo Monteiro | RM552616 |
| Iago Diniz | RM553776 |
| Rafael Nascimento | RM553117 |

---

Projeto desenvolvido para fins acadêmicos na **Global Solution 2026/1 - FIAP**.