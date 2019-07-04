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

### Example request

```
POST http://localhost:9000/graphql

query {
  owners {
    name, isHeavy
    products {
      name
      reversedName
    }
  }
  products {
    name
    description
    owner {
      name
    }
  }
}
```

returns

```
{
  "data": {
    "owners": [
      {
        "name": "Big Boy",
        "isHeavy": true,
        "products": [
          {
            "name": "Cheesecake",
            "reversedName": "ekaceseehC"
          },
          {
            "name": "Beef",
            "reversedName": "feeB"
          }
        ]
      },
      {
        "name": "Lil Boy",
        "isHeavy": false,
        "products": [
          {
            "name": "Health Potion",
            "reversedName": "noitoP htlaeH"
          }
        ]
      }
    ],
    "products": [
      {
        "name": "Cheesecake",
        "description": "Tasty",
        "owner": {
          "name": "Big Boy"
        }
      },
      {
        "name": "Health Potion",
        "description": "+50 HP",
        "owner": {
          "name": "Lil Boy"
        }
      },
      {
        "name": "Beef",
        "description": "Is Beef",
        "owner": {
          "name": "Big Boy"
        }
      },
      {
        "name": "Mystery Food",
        "description": "???",
        "owner": null
      }
    ]
  }
}
```

## Inspiration

Built largely based on the [Sangria Play tutorial](https://sangria-graphql.org/getting-started/#play-graphql-endpoint) and [howtographql's Scala relations article](https://www.howtographql.com/graphql-scala/7-relations/).

