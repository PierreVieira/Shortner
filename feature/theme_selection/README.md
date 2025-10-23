# 🎨 Theme Selection - Seleção de Temas

Este módulo implementa a funcionalidade de **seleção de temas**, permitindo aos usuários escolher entre diferentes temas da aplicação.

## 🎯 Objetivo

A feature de seleção de temas fornece:
- ✅ **Múltiplas opções** de tema
- ✅ **Prévia visual** dos temas
- ✅ **Persistência** da escolha
- ✅ **Aplicação dinâmica**
- ✅ **Material You** support

## 🏗️ Arquitetura

### 📊 **Data Layer**
- **ThemeRepository** - Repositório de temas
- **DataStore** - Persistência de preferências

### 🧠 **Domain Layer**
- **GetCurrentTheme** - Recuperar tema atual
- **SetTheme** - Definir novo tema
- **GetAvailableThemes** - Listar temas disponíveis

### 🎨 **Presentation Layer**
- **ThemeSelectionViewModel** - Gerenciamento de estado
- **ThemeSelectionScreen** - Interface de seleção
- **ThemeOption** - Componente de opção

## 🔧 Funcionalidades Principais

### 🎨 **Opções de Tema**
- Light Theme
- Dark Theme 
- Material You (quando disponível)

### 👀 **Prévia Visual**
Mostra preview das cores de cada tema antes da seleção.

### 💾 **Persistência**
Salva a escolha do usuário usando DataStore.

## 🔗 Dependências

- **Core DataStore** - Persistência de preferências
- **Core Model** - Modelos de tema
- **UI Theme** - Sistema de temas

---

[⬅️ Voltar às Features](../README.md) | [🎭 Ver Material You](../material_you/README.md)