# 等价兼容：补充 (ProjectE Supplement)

为 [ProjectE-Integration](https://github.com/TagnumElite/ProjectE-Integration) 提供额外模组的兼容支持，将各模组的机器配方动态映射为等价交换 EMC，并为各模组的基础材料提供种子 EMC 值。

## 工作原理

- **动态配方映射**：通过 PEI 的 `ARecipeTypeMapper` + ProjectE 的 `@RecipeTypeMapper` 注解，将目标模组的机器配方（粉碎、合成、熔炼、充能、转化等）映射为 EMC。启动时 ProjectE 自动发现并实例化映射器。
- **种子 EMC 值**：为无法通过配方自动推导的"叶子"材料（矿石、晶体、稀有合金等）预设固定 EMC，确保配方链能完整解析。
- **可选依赖**：所有目标模组均设为可选依赖。只有实际安装的模组才会启用对应映射器，未安装则静默跳过。

## 兼容模组一览

| 模组 | modid | 映射的机器/配方 | 种子 JSON |
|---|---|---|---|
| Mekanism | `mekanism` | 粉碎机、富集仓、充能冶炼炉、组合机、锯木机 | ✅ |
| AE2 Lightning Tech | `ae2lt` | 苍穹转化、闪电装配、闪电模拟、过载处理、闪电转化（雷击合成） | ✅ |
| Extreme Reactors | `bigreactors` | 燃料处理机、流体化器（3 种子配方） | ✅ |
| AE2 CS | `ae2cs` | 晶体粉碎机、晶体聚集器、电路蚀刻机 | ✅ |
| Data Energistics | `data_energistics` | 数据充能器、数据重组机（含 AE2 流体处理） | ✅ |
| Mekanism Sun | `mekanismsun` | 嬗变机（Transmutation） | ✅ |
| Neo Eco AE | `neoecoae` | 集成工作站 | ✅ |
| Mekanism Extras | `mekanism_extras` | 机器配方由 Mekanism 映射器自动覆盖 | ✅ |
| AE2 OmniCells | `ae2omnicells` | 无自定义配方类型 | ✅ |
| Industrial Foregoing | `industrialforegoing` | 已由 PEI 内置兼容，无需额外处理 | — |

## 依赖

| 依赖 | 类型 |
|---|---|
| Minecraft 1.21.1 | 必需 |
| NeoForge 21.1.206+ | 必需 |
| [ProjectE](https://www.curseforge.com/minecraft/mc-mods/projecte) PE1.0.1+ | 必需 |
| [ProjectE-Integration](https://github.com/TagnumElite/ProjectE-Integration) 8.3.1+ | 必需 |
| 上述兼容模组 | 可选，按需安装 |

## 使用

将本 jar 及 `ProjectE_Integration` jar 放入 `mods` 目录，启动游戏即可。需确保 ProjectE 和 ProjectE-Integration 已安装。

进入游戏后：
1. ProjectE 会开始 EMC 计算
2. 各映射器自动处理对应机器配方，将输入材料 EMC 之和映射为输出物品 EMC
3. 种子 JSON 为基础材料提供初始 EMC，其余物品由合成配方自动推导

可在 ProjectE 配置中开启调试日志观察映射器加载情况。

## 项目结构

```
src/com/pecsupplement/
  PESupplement.java          (@Mod 主类, modid=pec_supplement)
  MekanismAddon.java         (Mekanism 5 个映射器)
  AE2LTAddon.java            (AE2LT 5 个映射器)
  BigReactorsAddon.java      (ExtremeReactors 2 个映射器)
  Ae2csAddon.java            (AE2CS 3 个映射器)
  DataEnergisticsAddon.java  (DataEnergistics 2 个映射器)
  MekanismsunAddon.java      (MekanismSun 1 个映射器)
  NeoEcoAEAddon.java         (NeoEcoAE 1 个映射器)
data/projecteintegration/pe_custom_conversions/
  mekanism_default.json
  ae2lt_default.json
  bigreactors_default.json
  ae2cs_default.json
  ae2omnicells_default.json
  data_energistics_default.json
  mekanism_extras_default.json
  mekanismsun_default.json
  neoecoae_default.json
META-INF/neoforge.mods.toml
build.ps1                    (离线编译脚本)
```

## 构建

离线编译，无需 Gradle 或网络。须具备 Java 21 及本地已安装的 NeoForge 1.21.1 整合包。

```powershell
powershell -File build.ps1
```

构建脚本自动定位整合包中的 Minecraft、NeoForge、ProjectE 及各目标模组 jar 作为 classpath，编译后打包到 `output/` 目录。

## 添加新模组兼容

1. 在此目录下放置目标模组 jar
2. 在 `src/com/pecsupplement/` 新建 `<Mod>Addon.java`，编写映射器类
3. 在 `data/.../pe_custom_conversions/` 新建 `<modid>_default.json`，编写种子 EMC 值
4. 更新 `META-INF/neoforge.mods.toml` 添加可选依赖
5. 更新 `build.ps1` 添加 jar 查找和 classpath
6. 运行 `build.ps1` 重新打包
