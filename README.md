# Scala GraphQL API

Example GraphQL API made with Scala Play and Sangria

## Running

```
sbt run
```

## Using

This API implements the following schema:

```
interface Identifiable {
  id: String!
}

type Product implements Identifiable {
  id: String!
  name: String!
  reversedName: String!
  description: String!
  owner: Owner
}

type Owner implements Identifiable {
  id: String!
  name: String!
  isHeavy: String!
  products: [Product]
}

type Query {
  product(id: String!): Product
  products: [Product]
  owner(id: String!): Owner
  owners: [Owners]
}
```

Once running, send requests to `POST /graphql` with a body with `Content-type: application/json`

## Inspiration

Built largely based on the [Sangria Play tutorial](https://sangria-graphql.org/getting-started/#play-graphql-endpoint) and [howtographql's Scala relations article](https://www.howtographql.com/graphql-scala/7-relations/).

