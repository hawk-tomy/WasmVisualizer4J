MAX_U32 = (1 << 32) - 1
MAX_U64 = (1 << 64) - 1
MAX_I32 = (1 << 31) - 1
MAX_I64 = (1 << 63) - 1
MIN_I32 = -(1 << 31)
MIN_I64 = -(1 << 63)


def generate_unsigned_case():
    cases: list[tuple[int, str]] = []
    with open("./original/u_case.txt") as f:
        for line in f.readlines():
            i, *_ = line.split()
            cases.append((int(i), line.strip()))

    u32_cases: list[str] = []
    u64_cases: list[str] = []

    for i, s in cases:
        if i < MAX_U32:
            u32_cases.append(s)
        if i < MAX_U64:
            u64_cases.append(s)

    with open("./u32_case.txt", "w") as f:
        print("\n".join(u32_cases), file=f)

    with open("./u64_case.txt", "w") as f:
        print("\n".join(u64_cases), file=f)


def generate_signed_case():
    cases: list[tuple[int, str]] = []
    with open("./original/i_case.txt") as f:
        for line in f.readlines():
            i, *_ = line.split()
            cases.append((int(i), line.strip()))

    i32_cases: list[str] = []
    i64_cases: list[str] = []

    for i, s in cases:
        if MIN_I32 < i < MAX_I32:
            i32_cases.append(s)
        if MIN_I64 < i < MAX_U64:
            i64_cases.append(s)

    with open("./i32_case.txt", "w") as f:
        print("\n".join(i32_cases), file=f)

    with open("./i64_case.txt", "w") as f:
        print("\n".join(i64_cases), file=f)


if __name__ == "__main__":
    generate_unsigned_case()
    generate_signed_case()
