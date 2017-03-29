program exProcedure;
var
   a, b, c,  min: integer;
   d : string;
procedure findMin(x, y, z: integer; var m: integer);

begin
   if x < y then
      m:= x
   else
      m:= y;

   if z < m then
      m:= z;
end;

begin
   a := 100;
   b := 200;
   c := 300;
   d := 'MOF';
   findMin(a, b, c, d); { o correto é findMin(int,int,int,int) }

end.
