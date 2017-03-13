program exProcedure;
var
   a, b, c,  min: integer;
   d, felipe : string;
procedure findMin(x, y, z : integer; var m: integer);
(* Finds the minimum of the 3 values *)

begin
   if x < y then
      m:= x
   else
      m:= y;

   if z < m then
      m:= z;
end; { end of procedure findMin }

begin
   findMIN(a, b, c, 'c'); (* Procedure call *)

end.
