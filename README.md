# Dispatch Two

**This software is under active development.**

This is a rewrite of the Dispatch HTTP Client based on the Java 11 HTTP Client.

Previous versions were based on different third party clients: first the Apache HTTP library
and then the AsyncHttpClient Library. In Java 11, Java released a *standard* HTTP library
effectively allowing me to create a zero-dependency version of Dispatch for Java 11 and up.

The Java 11 HTTP Client is much simpler than the AsyncHttpClient because it doesn't expose all
the layers of Netty. This is intended to be a breaking release when it comes out and I'm not
promising the upgrade path will be without a few bumps. However, where possible I will aim to
minimize the pain of transitioning to the Java 11 backend from Dispatch 1.

## License

This project is licensed under the LGPL 3.0 License.

## Author

This is a Matt Farmer project. You can read more from him on [Twitter](https://twitter.com/frmr_m)
and on his [blog](https://frmr.me).
