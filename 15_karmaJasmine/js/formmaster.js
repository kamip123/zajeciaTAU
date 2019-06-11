exports.formmaster = function(content) {
    let validateFName = function(x){
        let element = document.getElementById(x);
        let name = document.getElementById(x).value;

        if(name.length < 3 && name.length > 6){
            element.classList.add("invalid");
            return false;
        }

        if(name.charAt(0) !== name.charAt(0).toUpperCase() ){
            element.classList.add("invalid");
            return false;
        }

        return validateForm(x);;
    };

    let validateLName = function(x){
        let element = document.getElementById(x);
        let name = document.getElementById(x).value;
        
        if(x.length < 5 && x.length > 20){
            element.classList.add("invalid");
            return false;
        }

        if(name.charAt(0) !== name.charAt(0).toUpperCase() ){
            element.classList.add("invalid");
            return false;
        }
        
        return validateForm(x);
    };

    let validateForm = function(x){
        let element = document.getElementById(x);
        if(element.classList.contains('invalid')){
            return false;
        }
        return true;
    }

    return {
        validate:function(x){
            if(x === 'fname'){
                return validateFName(x);
            }else if(x === 'lname'){
                return validateLName(x);
            }
        }
    }
}