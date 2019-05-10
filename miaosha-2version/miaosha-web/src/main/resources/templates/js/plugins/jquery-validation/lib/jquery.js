(function() {

var parts = document.location.search.slice( 1 ).split( "&" ),
	length = parts.length,
	scripts = document.getElementsByTagName("script"),
	src = scripts[ scripts.length - 1].src,
	i = 0,
	current,
	version = "1.9.0",
	file = "http://code.jquery.com/jquery-git.js";

for ( ; i < length; i++ ) {
	current = parts[ i ].split( "=" );
	if ( current[ 0 ] === "jquery" ) {
		version = current[ 1 ];
		break;
	}
}

if (version != "git") {
	file = src.replace(/jquery\.js$/, "jquery-" + version + ".js");
}


document.write( "<script src='" + file + "'></script>" );

})();
