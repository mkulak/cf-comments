Comments via cloudflare workers in Kotlin

Original [post](https://blog.cloudflare.com/making-static-sites-dynamic-with-cloudflare-d1/)

Compile: 

```./gradlew compileProductionExecutableKotlinJs ```

Run locally:

```npx wrangler dev --local --persist```

Apply SQL locally:

```npx wrangler d1 execute comments-db --local --file src/schema.sql```

Deploy:

```npx wrangler publish```

