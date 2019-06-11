// js module
exports.toolbox = function() {
  // some sample function - this function will not be exported
    let reverseSimple = function(x){
      let ret = "";
        for (let i = 0; i < x.length; i++)
          ret = '' + x[i] + ret;
        return ret;
    };
    
    return {
      reverse:function(x){
        if (x.selector) {
          document.querySelectorAll(x.selector).forEach(
            (element) => {
              element.innerHTML = reverseSimple(element.innerHTML);
            }
          );
          return {};
        } else {
          return reverseSimple(x);
        }
      }
    };
  };
