# Load lpSolveAPI
require(lpSolveAPI)

# Set an empty linear problem
linProgram <- make.lp(nrow = 0, ncol = 3)
# Set the linear program as a maximanization
lp.control(linProgram, sense="max")


# Set type of decision variables
set.type(linProgram, 1:3, type=c("real"))
# Set objective function coefficients vector C
# 1 = chair
# 2 = armchair
# 3 = armchair 2
set.objfn(linProgram, c(30, 20,0))

# Add constraints
add.constraint(linProgram, c(2, 1, 0), "<=", 8)
add.constraint(linProgram, c(0,0,3), "<=", 8)
add.constraint(linProgram, c(0,1,-1), "=", 0)


# Display the LPsolve matrix
linProgram

# Solve problem
solve(linProgram)

# Get the variables
get.variables(linProgram)
# Get the value of the objective function
get.objective(linProgram)
