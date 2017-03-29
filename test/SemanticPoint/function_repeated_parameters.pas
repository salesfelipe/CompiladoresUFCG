program exFunction;
var
   a, b, max : integer;
   d, e, f : string;
function ret(num1, num2: integer; s1, s2 : string; num1 : string): integer;
                                        { O parametro num1 é declarado 2x* }
var
   result: integer;
   c : integer;
begin
   if (num1 > num2) then
      result := num1
   else
      result := num2;
   ret := result;
end;

begin
   a := 100;
   b := 200;
   d := 'rafael';
   e := 'felipe';
   f := 'ana';

   b := ret(a,b,d,e,f); {b := funcao inexistente}

end.
