program exFunction;
var
   a, b, max : integer;
   d, e, f : string;

function ret(num1, num2: integer; s1, s2, s3: string): integer;
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

   b := ret(a,e,f); { numero de parametros diferente do requerido por ret}
end.
