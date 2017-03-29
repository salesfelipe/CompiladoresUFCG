program exProcedure;
var
   a, b, c,  min: integer;

procedure findMax(x, y, z: integer);
var m : integer;
begin
   if x < y then
      m:= x
   else
      m:= y;

   if z < m then
      m:= z;
end;

begin
  findMax(a, b);  { findMax recebe 3 parametros }
end.
