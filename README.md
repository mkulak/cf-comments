Comments via cloudflare workers in Kotlin

Original [post](https://blog.cloudflare.com/making-static-sites-dynamic-with-cloudflare-d1/)

Compile: 

```shell
./gradlew buildProd 
```

Run locally:

```shell
wrangler dev --local 
```

Apply SQL locally:

```shell
wrangler d1 execute comments-db --local --file src/schema.sql
```

Deploy:

```shell 
wrangler deploy
```

