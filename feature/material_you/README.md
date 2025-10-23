# 🎭 Material You - Personalização Dinâmica

Este módulo implementa o suporte ao **Material You**, permitindo personalização dinâmica de cores baseada na wallpaper do sistema.

## 🎯 Objetivo

A feature Material You fornece:
- ✅ **Cores dinâmicas** baseadas no sistema
- ✅ **Adaptação automática** de paleta
- ✅ **Fallback** para temas customizados
- ✅ **Suporte multiplataforma**

## 🏗️ Arquitetura

### 📊 **Data Layer**
- **MaterialYouRepository** - Repositório de cores dinâmicas
- **PlatformColorProvider** - Provider específico de plataforma

### 🧠 **Domain Layer**
- **GetDynamicColors** - Recuperar cores dinâmicas
- **IsMaterialYouAvailable** - Verificar disponibilidade
- **ApplyDynamicTheme** - Aplicar tema dinâmico

### 🎨 **Presentation Layer**
- **MaterialYouProvider** - Provider de cores
- **DynamicColorScheme** - Esquema de cores dinâmico

## 🔧 Funcionalidades Principais

### 🎨 **Cores Dinâmicas**
Extrai cores da wallpaper do sistema quando disponível.

### 📱 **Suporte Multiplataforma**
- Android 12+ (Material You nativo)
- iOS 15+ (Adaptive colors)
- Desktop (Fallback para temas customizados)

### 🔄 **Fallback Inteligente**
Retorna para temas customizados quando Material You não está disponível.

## 🔗 Dependências

- **Core Model** - Modelos de tema
- **UI Theme** - Sistema de temas
- **Platform APIs** - APIs específicas de plataforma

---

[⬅️ Voltar às Features](../README.md) | [🎨 Ver Theme Selection](../theme_selection/README.md)