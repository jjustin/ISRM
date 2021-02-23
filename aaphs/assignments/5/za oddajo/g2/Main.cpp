#include <bits/stdc++.h>

#include <cmath>
using namespace std;

#define MOD (1000 * 1000 * 1000 + 7)
#define INF (1LL << 60)
#define pb push_back
#define po pop_back
#define fi first
#define se second

typedef pair<int, int> ii;
typedef vector<ii> vii;
typedef vector<int> vi;
typedef long long ll;
typedef long double ld;

// Num of nodes, max weight of truck
int N, M;
// Combined waste
array<double, 3> WC = {0, 0, 0};
// Coordinates for every node
vector<pair<double, double>> C;
// Waste for every node
vector<vector<double>> W;
// Graph{u, v, cost, weight}
vector<vector<pair<int, pair<double, double>>>> G;

// FloydWarshal distance for every truck weight
vector<vector<vector<double>>> FW;
// FloydWarshal path for every truck weight
vector<vector<vector<int>>> FWPath;
int visited[500];

// Resoults for {waste type, paths}
map<int, vector<vector<int>>> RES;

// NOT USED
vector<double> D1;
vector<vector<double>> D;

void readInput() {
  cin >> N >> M;

  C.assign(N, {0, 0});
  W.assign(N, vector<double>(3, 0.0));
  G.assign(N, vector<pair<int, pair<double, double>>>());
  FW.assign(M + 1, vector<vector<double>>(N, vector<double>(N, INF)));
  FWPath.assign(M + 1, vector<vector<int>>(N, vector<int>(N, -1)));

  for (int i = 0; i < N; ++i) {
    int temp;
    cin >> temp;
    cin >> C[i].fi >> C[i].se;
    for (int j = 0; j < 3; ++j) {
      cin >> W[i][j];
      WC[j] += W[i][j];
    }
  }

  int u, v, o;
  double c, w;
  while (cin >> u) {
    cin >> v >> c >> o >> w;
    --u;
    --v;
    G[u].pb({v, {c, w}});

    for (int i = 0; i <= M; ++i) {
      if (floor(w) > i) {
        FW[i][u][v] = min(FW[i][u][v], c);
        FWPath[i][u][v] = v;
      }
    }

    if (o == 0) {
      G[v].pb({u, {c, w}});
      for (int i = 0; i <= M; ++i) {
        if (floor(w) > i) {
          FW[i][v][u] = min(FW[i][v][u], c);
          FWPath[i][v][u] = u;
        }
      }
    }
  }

  for (int i = 0; i < N; ++i) {
    for (int j = 0; j <= M; ++j) {
      FW[j][i][i] = 0;
      FWPath[j][i][i] = i;
    }
  }
}

/*
void distance() {
  D1.assign(N, 0.0);
  for (int i = 1; i < N; ++i) {
    D1[i] = (C[0].fi - C[i].fi) * (C[0].fi - C[i].fi) +
            (C[0].se - C[i].se) * (C[0].se - C[i].se);
    D1[i] = sqrt(D1[i]);
    if (D1[i] / 50.0 > 8.0) {
      D1[i] = D1[i] * 0.1 + (D1[i] / 50 - 8.0) * 20.0 + 10.0 + 10.0;
      D1[i] = 8.0 * 10.0;
    } else {
      D1[i] = D1[i] * 0.1 + (D1[i] / 50.0) * 10.0 + 5.0 + 10.0;
    }
  }
}

void distanceMatrix() {
  D.assign(N, vector<double>(N, 0.0));
  for (int i = 0; i < N; ++i) {
    for (int j = i; j < N; ++j) {
      D[i][j] = (C[j].fi - C[i].fi) * (C[j].fi - C[i].fi) +
                (C[j].se - C[i].se) * (C[j].se - C[i].se);
      D[i][j] = sqrt(D[i][j]);
      D[j][i] = D[i][j];
    }
  }
}
*/

void FloydWarshal(int w) {
  for (int k = 0; k < N; ++k) {
    for (int i = 0; i < N; ++i) {
      for (int j = 0; j < N; ++j) {
        if (FW[w][i][j] > FW[w][i][k] + FW[w][k][j]) {
          FW[w][i][j] = FW[w][i][k] + FW[w][k][j];
          FWPath[w][i][j] = FWPath[w][i][k];
        }
      }
    }
  }
}

void initFloydWarshal() {
  for (int i = 0; i <= M; ++i) FloydWarshal(i);
}

inline vector<int> pathFromNodeToNode(const int w, int u, const int &v) {
  if (FWPath[w][u][v] == -1) return {};
  vector<int> path;
  path.pb(u);
  while (u != v) {
    u = FWPath[w][u][v];
    path.pb(u);
  }
  return path;
}

double heuristic(const int wt, const int u, const int v, const double w) {
  double dist = FW[ceil(w)][u][v];
  double picked = W[v][wt];
  if (dist == 0) return picked;
  return picked / dist;
}

inline int findNextNode(const int &wt, const int &u, const double &w) {
  int new_node = -1;
  int f = 1 + (rand() % 100);

  if (f > 50) {
    double dist = 0;

    for (int i = 0; i < N; ++i) {
      if (u == i || visited[i] || FWPath[ceil(w)][u][i] == -1) continue;
      double mm = (double)M;
      if ((w + W[i][wt] - mm) > 0.0) continue;
      double t = heuristic(wt, u, i, w);
      if (t > dist) {
        dist = t;
        new_node = i;
      }
    }
  } else {
    double dist = INF;

    for (int i = 0; i < N; ++i) {
      if (u == i || visited[i] || FWPath[ceil(w)][u][i] == -1) continue;
      double mm = (double)M;
      if ((w + W[i][wt] - mm) > 0.0) continue;
      if (FW[ceil(w)][u][i] < dist) {
        dist = FW[ceil(w)][u][i];
        new_node = i;
      }
    }
  }

  return new_node;
}

struct Ret {
  vector<int> path;
  vector<int> picked;
  double d = 0;
  double w = 0;
  int cnt = 0;
};

void createCopyStruct(struct Ret &t, struct Ret &r) {
  t.path = vector<int>(r.path);
  t.picked = vector<int>(r.picked);
  t.d = r.d;
  t.w = r.w;
  t.cnt = r.cnt;
}

Ret pathFinding(const int &wt) {
  Ret r;
  int u = 0;
  r.w = 0;
  r.cnt = 0;
  while (true) {
    int v = findNextNode(wt, u, r.w);
    if (v == -1) break;

    auto p = pathFromNodeToNode(ceil(r.w), u, v);
    bool cont = false;
    for (int i = 0; i < p.size() - 1; ++i) {
      if (visited[p[i]] == 0 && ((double)M - r.w) >= W[p[i]][wt]) {
        visited[p[i]] = 1;
        r.d += FW[ceil(r.w)][u][p[i]];
        r.cnt++;
        r.picked.pb(p[i]);
        r.w += W[p[i]][wt];
        u = p[i];
        cont = true;
        break;
      }
      r.path.pb(p[i]);
    }

    if (cont) continue;

    if (visited[v] == 0) {
      r.picked.pb(v);
      r.cnt++;
    }

    visited[v] = 1;
    r.w += W[v][wt];
    r.d += FW[ceil(r.w)][u][v];
    u = v;
  }
  auto p = pathFromNodeToNode(r.w, u, 0);
  r.d += FW[ceil(r.w)][u][0];
  for (auto &i : p) r.path.pb(i);

  return r;
}

void clean(struct Ret &t) {
  for (auto &i : t.picked) {
    visited[i] = 0;
  }
}

vector<vector<int>> solve(const int &wt) {
  vector<vector<int>> paths;
  memset(visited, 0, sizeof(visited));

  int cnt = 0;
  while (cnt < N) {
    Ret t = pathFinding(wt);
    clean(t);
    double cost = t.w / t.d;

    for (int i = 0; i < 20; ++i) {
      Ret p = pathFinding(wt);
      clean(p);
      double t_cost = p.w /  p.d;

      if (t_cost > cost) {
        // cout << t_cost << " " << cost << endl;
        createCopyStruct(t, p);
        cost = t_cost;
      }
    }

    for (auto &j : t.picked) visited[j] = 1;
    vector<int> s(t.path);
    paths.pb(s);

    cnt += t.cnt;
  }

  /*if (abs(glw - WC[wt]) > 1e-6) {
    cout << "ERROR: didn't pick all waste" << endl;
    cout << setprecision(10) << glw << " " << WC[wt] << endl;
  }*/
  // cout << setprecision(10) << glw << " " << WC[wt] << endl;

  return paths;
}

void solve() {
  for (int i = 0; i < 3; ++i) RES[i] = solve(i);
}

void printRest() {
  for (auto &i : RES) {
    for (auto &j : i.se) {
      cout << i.fi + 1;
      for (auto &k : j) cout << "," << k + 1;
      cout << "\n";
    }
  }
  cout << flush;
}

void info() {
  for (auto &i : WC) {
    cout << i << endl;
  }

  for (auto &i : FW[0]) {
    for (auto &j : i) {
      cout << j << " ";
    }
    cout << endl;
  }

  /*for(auto &c: Cycles) {
    for(auto &i: c) {
      cout << i + 1<< " ";
    }
    cout << endl;
  }*/
}

int main(int argc, char *argv[]) {
  ios_base::sync_with_stdio(false);
  cin.tie(nullptr);

  if (argc == 3) {
    freopen(argv[1], "r", stdin);
    freopen(argv[2], "w", stdout);
  } else {
    freopen("Problems/Problem2.txt", "r", stdin);
    // freopen("Solutions/solution2.txt", "w", stdout);
  }

  srand(123);
  readInput();
  initFloydWarshal();
  solve();
  printRest();

  return 0;
}
