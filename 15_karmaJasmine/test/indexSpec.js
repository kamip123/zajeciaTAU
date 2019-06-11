const {toolbox} = require("../js/toolbox.js"); // load module
const {formmaster} = require("../js/formmaster.js");

describe("cenzor", function () {
    
    jasmine.clock().install();

    const tools = toolbox();
    const forms = formmaster();

    describe('valid form', function () {
        beforeEach(function () {
            let s = spyOn(console, 'log').and.callThrough();
            let elem = document.createElement('div');
            elem.id = "mycontainer";
            elem.innerHTML = `
            <div class="tcz">ABC</div>
            <div class="tcz">BCD</div>
            <div>
                <form>
                    First name:<br>
                    <input type="text" value="Jan" id="fname"><br>
                    Last name:<br>
                    <input type="text" id="lname" value="Kowalski"><br>
                    <input type="submit" value="Submit">
                </form>
            </div> 
            `;
            document.body.appendChild(elem)
        });

        afterEach(function () {
            $('#mycontainer').remove();
        });

        it("shold validate form values", function () {
            console.log("Testing form values");
            expect(forms.validate('fname')).toBe(true);
            expect(forms.validate('lname')).toBe(true);
        });

        it("shold validate form", function () {
            console.log("Testing form values");
            expect(forms.validate('fname')).toBe(true);
            expect(forms.validate('lname')).toBe(true);
            expect(document.getElementById('fname').classList.contains('invalid')).toBe(false);
            expect(document.getElementById('lname').classList.contains('invalid')).toBe(false);
        });
    });

    describe('invalid form', function () {
        beforeEach(function () {
            let s = spyOn(console, 'log').and.callThrough();
            let elem = document.createElement('div');
            elem.id = "mycontainer";
            elem.innerHTML = `
            <div class="tcz">ABC</div>
            <div class="tcz">BCD</div>
            <div>
                <form>
                    First name:<br>
                    <input type="text" value="jan" id="fname"><br>
                    Last name:<br>
                    <input type="text" id="lname" value="kowalski"><br>
                    <input type="submit" value="Submit">
                </form>
            </div> 
            `;
            document.body.appendChild(elem)
        });
        
        afterEach(function () {
            $('#mycontainer').remove();
        });

        it("shold validate form", function () {
            console.log("Testing form values");
            expect(forms.validate('fname')).toBe(false);
            expect(forms.validate('lname')).toBe(false);
            expect(document.getElementById('fname').classList.contains('invalid')).toBe(true);
            expect(document.getElementById('lname').classList.contains('invalid')).toBe(true);
        });
    });
});

