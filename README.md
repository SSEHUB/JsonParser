JsonParser
==========

A simple JSON Parser written in Java without any dependencies.

## Usage

The JSON data structure inherits from the abstract class `net.ssehub.json.JsonElement`.
Implement `net.ssehub.json.JsonVisitor` for traversal using the visitor pattern.

Parsing:
```Java
try {
	net.ssehub.json.JsonParser parser = new net.ssehub.json.JsonParser(fileOrStream);
	net.ssehub.json.JsonElement result = parser.parse();
} catch (IOException | net.ssehub.json.ParsingException e) {
	/* ... */
}
```

Writing:
```Java
net.ssehub.json.JsonElement json = /* ... */;
String s1 = json.toString(); // short representation
String s2 = json.accept(new net.ssehub.json.JsonPrettyPrinter()); // pretty representation
```

## License

This project is licensed under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0.html).
