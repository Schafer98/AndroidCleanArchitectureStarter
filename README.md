# AndroidArchitect

A bare-bones, fully-wired Android starter project for practicing Clean Architecture. All the tedious plumbing is done — Compose, Hilt, Retrofit, Navigation, Lifecycle, KSP — so you can spend your time on the parts that actually teach you something: structuring layers, defining boundaries, and writing code you can throw away on purpose.

## What it is

A blank canvas with the boring setup already complete. The current `MainActivity` shows a single "Hello, Android!" Compose screen and nothing else. Pick an API, design your `data` / `domain` / `presentation` layers, and build whatever feature you want on top of it. When you're done, save your attempt and reset to the baseline so you can try a different approach.

## What's wired up

- **UI**: Jetpack Compose + Material 3
- **DI**: Hilt (with KSP-based code generation)
- **Networking**: Retrofit + OkHttp (with logging interceptor) + Gson converter
- **Lifecycle**: ViewModel + `collectAsStateWithLifecycle`
- **Navigation**: Navigation Compose
- **Build**: AGP 8.7.3, Kotlin 2.0.21, Gradle 8.10.2, KSP 2.0.21-1.0.28

`compileSdk` and `targetSdk` are 35; `minSdk` is 26.

## Getting started

1. **Install Android Studio** (Ladybug or newer recommended).
2. **Clone the repo** and open the root folder in Android Studio. The IDE will trigger the first Gradle sync automatically.
3. **Set the Gradle JDK** (if Android Studio doesn't pick it up): `Settings → Build, Execution, Deployment → Build Tools → Gradle → Gradle JDK` → pick the bundled JDK 17.
4. **Install Android SDK 35** if you don't already have it: `SDK Manager → SDK Platforms → Android 15.0`.
5. **Run** on an emulator or a physical device running API 26+.

## Suggested practice workflow

The whole point of this template is that it's reusable. When you finish a practice attempt, save it somewhere you can come back to, then reset to the baseline.

**Option A — branch (recommended for comparing attempts later):**

```bash
git checkout -b attempt-weather-mvvm
git add . && git commit -m "Weather app, MVVM + Clean"
git checkout main          # back to the blank slate
```

Branches stick around until you delete them, so weeks later you can `git checkout attempt-weather-mvvm` and compare it with your latest attempt.

**Option B — stash (lighter weight, transient):**

```bash
git stash push -u -m "weather attempt 1"
git stash list             # see your stashes
git stash apply stash@{0}  # bring it back later
```

Stashes survive checkouts but are easier to lose track of — write down what's in each one.

Either way, the cycle is the same: build → save → reset → try a different approach.

## Free APIs to practice with

All of these work without an API key (or have a generous keyless tier) and return clean JSON:

| API | Use it for | Docs |
|---|---|---|
| **Open-Meteo** | Weather forecasts, geocoding — no key, ever | https://open-meteo.com/ |
| **JSONPlaceholder** | Generic posts/users/comments — REST playground | https://jsonplaceholder.typicode.com/ |
| **PokéAPI** | Pokémon list + detail, great for paging practice | https://pokeapi.co/ |
| **REST Countries** | Country info, flags, currencies | https://restcountries.com/ |
| **Dog CEO** | Random dog images by breed | https://dog.ceo/dog-api/ |
| **TheMealDB** | Recipes, search, categories | https://www.themealdb.com/api.php |
| **CoinGecko** | Crypto prices and market data (free tier covers basics) | https://www.coingecko.com/en/api |

A useful exercise: build the same screen against the same API three different ways (e.g. plain MVVM, MVI, MVVM + UseCases). The comparison is where the learning compounds.

## Project structure

Currently flat — you decide how to slice it. A typical Clean Architecture cut would be:

```
app/src/main/java/.../
├── data/          # Retrofit interfaces, DTOs, repository implementations
├── domain/        # Use cases, domain models, repository contracts
├── presentation/  # Composables, ViewModels, UI state
└── di/            # Hilt modules
```

That's a starting point, not a prescription. Try other layouts (feature-based, by-screen) and see what fits.
