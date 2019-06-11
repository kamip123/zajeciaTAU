
(function( $ ) {
 
    $.fn.cenzor = function() {
         this.each(function() {
            var elem = $( this );
            elem.text('--censored--');//.append( " (" + link.attr( "href" ) + ")" );
        });
         return this;
     };
 
}( jQuery ));
