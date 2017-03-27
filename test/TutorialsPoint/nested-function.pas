program exFunction;
var
   a, b, ret : real;

function E(x: real): real;
    function F(y: real): real;
    begin
        F := x + y
    end;
begin
    E := F(3) + F(4)
end;

begin
   a := 100;
   b := 200;
   (* calling a function to get max value *)
   ret := E(a);

end.
