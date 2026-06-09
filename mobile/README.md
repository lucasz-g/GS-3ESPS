# OrbitGuard Mobile

## Visão Geral

O **OrbitGuard Mobile** é o aplicativo frontend da plataforma OrbitGuard, desenvolvido com **React Native**, **Expo** e **TypeScript**.

O OrbitGuard é uma plataforma acadêmica de monitoramento de riscos climáticos integrada às disciplinas de **Arquitetura Orientada a Serviços (SOA)** e **Desenvolvimento Mobile**.

A solução completa é composta por:

- **Backend:** API REST desenvolvida com Spring Boot
- **Frontend:** aplicativo mobile desenvolvido com React Native e Expo

O objetivo do app é permitir que usuários façam login, cadastrem locais de interesse, consultem relatórios simplificados de risco climático e acessem um histórico de consultas.

---

## Arquitetura da Solução

```text
┌─────────────────────┐
│   Mobile App        │
│ React Native + Expo │
└──────────┬──────────┘
           │ HTTP/REST
           ▼
┌─────────────────────┐
│   OrbitGuard API    │
│ Spring Boot         │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ Banco de Dados      │
│ H2 / PostgreSQL     │
└─────────────────────┘

           │
           ▼
┌─────────────────────┐
│ Weather Service     │
│ OpenWeather (Mock)  │
└─────────────────────┘
```

---

## Tecnologias do Frontend

- React Native
- Expo
- TypeScript
- React Navigation
- Axios
- AsyncStorage
- Context API para alternância de tema

---

## Funcionalidades Implementadas no App

### Autenticação

- Tela de login
- Tela de cadastro
- Armazenamento do token JWT no AsyncStorage
- Envio do token nas próximas requisições

### Dashboard

- Exibição do relatório de risco mais recente
- Atalhos para locais monitorados e histórico
- Botão no cabeçalho para alternar entre tema claro e tema escuro

### Gestão de Locais

- Listagem de locais monitorados
- Cadastro de novos locais
- Validação simples de latitude e longitude
- Navegação para detalhes do local

### Monitoramento Climático

- Consulta do relatório de risco por local
- Exibição de temperatura, vento, chuva, condição climática, nível de risco e recomendação
- Histórico de relatórios

### Suporte Offline e Mock

- Cache local do relatório mais recente por local usando AsyncStorage
- Dados mockados quando o backend não está configurado ou não está executando
- O mock cobre login, cadastro, locais, criação de local, histórico e detalhe de risco

### Tema

- Tema claro
- Tema escuro
- Alternância manual pelo botão de tema
- Aplicação das cores nas telas, cards, inputs e cabeçalho de navegação

---

## Observações Sobre Escopo

As funcionalidades de atualização, exclusão e favoritos fazem parte da visão geral da plataforma, mas **não estão implementadas nesta versão do frontend mobile**.

Nesta versão, o foco do app está em autenticação, cadastro/listagem de locais, consulta de risco, histórico e fallback mock para demonstração sem backend.

---

## Estrutura do Projeto

```text
mobile/
├── App.tsx
├── package.json
├── tsconfig.json
└── src/
    ├── navigation/
    │   └── AppNavigator.tsx
    ├── screens/
    │   ├── AddLocationScreen.tsx
    │   ├── HistoryScreen.tsx
    │   ├── HomeScreen.tsx
    │   ├── LocationDetailScreen.tsx
    │   ├── LocationsScreen.tsx
    │   ├── LoginScreen.tsx
    │   └── RegisterScreen.tsx
    ├── services/
    │   ├── api.ts
    │   └── mockApi.ts
    ├── theme/
    │   └── ThemeContext.tsx
    └── storage/
        └── reportStorage.ts
```

---

## Integração com Backend

O cliente HTTP fica em:

```text
src/services/api.ts
```

A URL base atual é:

```text
http://localhost:8080
```

Caso o app seja executado em um celular físico ou emulador, pode ser necessário trocar `localhost` pelo IP da máquina onde o backend está rodando.

Endpoints consumidos pelo frontend:

```text
POST /auth/login
POST /auth/register
GET  /locations
POST /locations
GET  /risk/{id}
GET  /risk/history
```

Se o backend não responder, o interceptor do Axios usa os dados mockados em:

```text
src/services/mockApi.ts
```

---

## Como Executar

### Pré-requisitos

- Node.js instalado
- npm instalado
- Expo Go no celular ou ambiente de emulação configurado

### Instalação

```bash
npm install
```

### Executar com Expo

```bash
npm start
```

### Executar no Android

```bash
npm run android
```

### Executar no iOS

```bash
npm run ios
```

---

## Validação Técnica

Para verificar erros de TypeScript:

```bash
npx tsc --noEmit
```

---

## Fluxo Principal do App

```text
Login
  ├── Cadastro
  └── Dashboard
        ├── Locais Monitorados
        │     ├── Adicionar Local
        │     └── Detalhe do Local
        └── Histórico de Relatórios
```

O botão de tema fica disponível na tela de login e no cabeçalho das demais telas.

---

## Modo Mock

O modo mock não precisa ser ativado manualmente. O app tenta chamar o backend primeiro. Se não houver resposta do servidor, os dados simulados são retornados automaticamente.

Esse comportamento facilita a apresentação e os testes do frontend mesmo quando a API Spring Boot não estiver executando.
