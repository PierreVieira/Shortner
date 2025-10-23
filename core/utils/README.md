# 🛠️ Utils - Utilitários e Extensões

Este módulo contém **utilitários** e **extensões** que podem ser reutilizados em todo o projeto. Fornece funções auxiliares, extensões Kotlin customizadas e helpers para operações comuns.

## 🎯 Objetivo

Os Utils fornecem:
- ✅ **Funções auxiliares** reutilizáveis
- ✅ **Extensões Kotlin** customizadas
- ✅ **Operações comuns** centralizadas
- ✅ **Código limpo** e organizado

## 📋 Componentes Principais

### 🔤 **StringUtils**
Utilitários para manipulação e formatação de strings.

**Funcionalidades:**
- **Remoção de HTML** de strings (para summaries)
- **Truncamento** de texto com ellipsis
- **Formatação de datas** da API
- **Capitalização** de texto

### 🔄 **FlowUtils**
Utilitários para manipulação de Flows e StateFlow.

**Funcionalidades:**
- **Update de StateFlow** preservando referência
- **Combine de Flows** para operações complexas
- **Debounce** para otimização de performance
- **Extensões** para operações comuns

### 📊 **DateUtils**
Utilitários para manipulação e formatação de datas.

**Funcionalidades:**
- **Parsing** de datas da API TVMaze
- **Formatação** para exibição na UI
- **Validação** de strings de data
- **Conversões** entre formatos

### 🖼️ **ImageUtils**
Utilitários para manipulação de URLs de imagem.

**Funcionalidades:**
- **Seleção automática** de URL otimizada
- **Validação** de URLs de imagem
- **Placeholders** para imagens ausentes
- **Fallbacks** para diferentes resoluções

## 🔧 Extensões Kotlin

### 📋 **DataStatus Extensions**
Extensões para manipulação de `DataStatus<T>`.

### 🔄 **Flow Extensions**
Extensões para operações com Flows.

### 📱 **Compose Extensions**
Extensões para componentes Compose.

## 🔗 Dependências

- **Kotlinx Coroutines** - Para operações assíncronas
- **Kotlinx DateTime** - Para manipulação de datas
- **Jetpack Compose** - Para extensões de UI
- **Core Model** - Para tipos de domínio

---

[⬅️ Voltar ao Core](../README.md) | [🔧 Ver Providers](../provider/README.md) | [🎨 Ver UI](../../ui/README.md)
