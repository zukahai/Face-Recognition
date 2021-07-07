# A = []
# B = [0] * 10
# def converInt(s):
#     sum = 0
#     if s in A:
#         B[A.index(s)] += 1
#     else :
#         A.append(s)
#         B[A.index(s)] += 1

# kt2 = True
# kt = True

# def PP(N):
#     kt2 = True
#     kt = kt2
#     for i in range(0, N + 1):
#         if kt:
#             print('A')
#             kt = False
#     kt2 = kt

# PP(10)

f = open("C:\\xampp\\htdocs\\HaiZuka\\data.txt",'r',encoding = 'utf-8')
str = f.read()
data = str.split("\n")
for i in range(len(data)):
    data[i] = (data[i]).split(" @#$ ")
print(data)
data[0][0] = 'w' + data[0][0]
f.close()

f = open('C:\\xampp\\htdocs\\HaiZuka\\data.txt','w')

for i in range(len(data)):
    data[i] = " @#$ ".join(data[i])
data = "\n".join(data)
f.write(data)
# dong file
f.close()